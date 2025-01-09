import { Component, OnInit,ApplicationRef } from '@angular/core';
import { CartService } from '../../service/card.service';
import { ProductService } from '../../service/product.service';
import { CommonModule } from '@angular/common';
import { Product } from '../../responses/user/product';
import { HttpClientModule } from '@angular/common/http';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { OrderService } from '../../service/order.service';
import { OrderDTO } from '../../responses/user/orderDTO';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { TokenService } from '../../service/token.service';
import { HeaderComponent } from '../../header/header.component';
import { FooterComponent } from '../../footer/footer.component';
import { FormsModule } from '@angular/forms';

import { ChangeDetectorRef } from '@angular/core';







@Component({
  selector: 'app-order',
  standalone: true,
  imports: [CommonModule, HttpClientModule,ReactiveFormsModule,RouterModule,HeaderComponent,FooterComponent,FormsModule],
  templateUrl: './oder.component.html',
  styleUrls: ['./oder.component.scss'] // Chỉnh sửa thành styleUrls
})
export class OderComponent implements OnInit {
  orderForm! :FormGroup;
  cartItems: { product: Product; quantity: number }[] = []; // Thông tin giỏ hàng
  // couponCode:'';
  user:any;
  totalAmount: number = 0; // Tổng tiền
  discountAmount: number = 0; // Số tiền giảm giá
  finalAmount: number = 0; // Tổng tiền sau giảm giá
  couponCode: string = ''; // Mã giảm giá người dùng nhập
  orderData: OrderDTO = {
    id:0,
    user_id: 0, // Thay bằng user_id thích hợp
    fullname: '',
    email: '',
    phone_number: '',
    address: '',
    note: '',
    total_money: 0,
    payment_method: 'cod', // Mặc định là thanh toán khi nhận hàng (COD)
    shipping_method: 'express', // Mặc định là vận chuyển nhanh
    coupon_code: '', // Mã giảm giá
    order_date: new Date(),
    active:'',
    cart_items: [],// Mảng chứa sản phẩm trong giỏ hàng
    orderDetail: [], 
  };

  constructor(
    private cartService: CartService,
    private productService: ProductService,
    private orderService: OrderService,
    private fb:FormBuilder,
    private appRef: ApplicationRef,
    private router:Router,
    private tokenService:TokenService,
    private cdr: ChangeDetectorRef

  ) {
   
  }

  ngOnInit(): void {
     this.appRef.isStable.subscribe((isStable: any) => {
        if (isStable) {
            console.log('Ứng dụng đã ổn định');
        }
    });
    this.orderForm = this.fb.group({
      fullname: ['', Validators.required],  // Bắt buộc nhập họ tên
      email: ['', [Validators.required, Validators.email]],  // Bắt buộc nhập email đúng định dạng
      phone_number: ['', [Validators.required, Validators.minLength(6)]],  // Bắt buộc nhập và ít nhất 6 ký tự
      address: ['', [Validators.required, Validators.minLength(5)]],  // Bắt buộc nhập và ít nhất 5 ký tự
      note: [''],  // Không bắt buộc
      shipping_method: ['express', Validators.required],  // Bắt buộc chọn phương thức vận chuyển
      payment_method: ['cod', Validators.required],  // Bắt buộc chọn phương thức thanh toán
      
    });
    debugger;
    //this.cartService.clearCart();
    this.orderData.user_id=this.tokenService.getUserId();
    debugger 
    const cart = this.cartService.getCart();
    console.log('Cart data:', cart);
    const productIds = Array.from(cart.keys()); // Chuyển danh sách ID từ Map giỏ hàng

    
      // Gọi service để lấy thông tin sản phẩm dựa trên danh sách ID
if(productIds.length===0){
  return;
}
this.productService.getProductsByIds(productIds).subscribe({
  next: (products: Product[]) => {
    console.log('Fetched products:', products);

    // Lấy thông tin sản phẩm và số lượng từ danh sách sản phẩm và giỏ hàng
    this.cartItems = productIds
      .map((productId) => {
        const product = products.find((p) => p.id === productId);
        if (product) {
          product.thumbnail = `http://localhost:8080/api/v1/products/images/${product.thumbnail}`;
          return {
            product: product,
            quantity: cart.get(productId) || 1,
          };
        } else {
          console.warn(`Sản phẩm với ID ${productId} không tồn tại trong dữ liệu trả về từ API.`);
          return null; // Bỏ qua sản phẩm không hợp lệ
        }
      })
      .filter((item) => item !== null) as { product: Product; quantity: number }[];

    // Tính tổng tiền sau khi đã cập nhật cartItems
    this.calculateTotal();
  },
  error: (error: any) => {
    console.error('Error fetching detail:', error);
  },
});
  
  }
  placeOrder(): void {
    debugger; // Kiểm tra trạng thái ban đầu
    if (this.orderForm.valid) {
      // Lấy userId từ thông tin người dùng đã lấy từ token
      const token = this.tokenService.getToken();
      console.log('Token from localStorage:', token); // Kiểm tra token
      
      const userId = this.tokenService.getUserId();
      console.log('User ID from token:', userId);
      
      if (userId == null || userId <= 0) {
        alert('Không thể xác định người dùng. Vui lòng đăng nhập lại.');
        this.router.navigate(['/login']);
        return;
      }
  
      // Kiểm tra từng sản phẩm trong giỏ hàng trước khi đặt hàng
      let isValid = true;
      const cartItemsWithValidation = [...this.cartItems]; // Tạo bản sao để xử lý
  
      for (const cartItem of this.cartItems) {
        const productId = cartItem.product.id!;
        const requestedQuantity = cartItem.quantity;
  
        // Gọi API kiểm tra số lượng tồn kho của sản phẩm
        this.productService.getProductById(productId).subscribe({
          next: (product: Product) => {
            const availableQuantity = parseInt(product.quantity, 10); // Chuyển đổi quantity sang kiểu số nguyên
            if (availableQuantity <= 0) {
              // Sản phẩm hết hàng
              alert(`Sản phẩm ${product.name} đã hết hàng.`);
              isValid = false; // Đánh dấu là không hợp lệ
            } else if (requestedQuantity > availableQuantity) {
              // Số lượng yêu cầu vượt quá số lượng tồn kho
              alert(`Số lượng sản phẩm ${product.name} không đủ. Vui lòng chọn số lượng nhỏ hơn hoặc bằng ${availableQuantity}.`);
              isValid = false; // Đánh dấu là không hợp lệ
            }
          },
          error: (error: any) => {
            // Xử lý lỗi khi lấy thông tin sản phẩm
            console.error('Error fetching product details:', error);
            alert('Lỗi khi kiểm tra sản phẩm. Vui lòng thử lại.');
            isValid = false;
          }
        });
        
        
  
        // Nếu sản phẩm không hợp lệ, dừng lại và không tiếp tục đặt hàng
        if (!isValid) {
          return;
        }
      }
  
      // Nếu tất cả các sản phẩm đều hợp lệ, chuẩn bị dữ liệu orderData
      if (isValid) {
        this.orderData = {
          ...this.orderData,
          ...this.orderForm.value,
          user_id: userId,  // Gán userId vào orderData
          total_money: this.finalAmount
        };
  
        // Kiểm tra xem giỏ hàng có chứa sản phẩm không
        if (!this.cartItems || this.cartItems.length === 0) {
          alert('Giỏ hàng trống. Vui lòng thêm sản phẩm vào giỏ hàng trước khi đặt hàng.');
          return;
        }
  
        // Map lại dữ liệu cart_items
        this.orderData.cart_items = this.cartItems.map(cartItem => ({
          product_id: cartItem.product.id!,
          quantity: cartItem.quantity
        }));
  
        // Gán giá trị total_money
        this.orderData.total_money = this.totalAmount;
        
        // Log dữ liệu để kiểm tra trước khi gửi
        console.log('Dữ liệu trước khi gửi:', this.orderData);
  
        // Gửi yêu cầu đặt hàng
        this.orderService.placeOrder(this.orderData).subscribe({
          next: (response: any) => {
            console.log('Đặt hàng thành công', response);
            alert('Đặt hàng thành công');
            this.cartService.clearCart();
            this.router.navigate(['/']);
          },
          complete: () => {
            console.log('Yêu cầu hoàn tất');
            this.calculateTotal();
          },
          error: (error: any) => {
            console.error('Chi tiết lỗi từ API:', error);
        
            // Lấy thông báo lỗi từ trường error
            const errorMessage =
                error.error || // Nếu API trả về thông báo lỗi trong error.error
                'Lỗi khi đặt hàng. Vui lòng thử lại.'; // Thông báo mặc định
        
            // Hiển thị thông báo lỗi
            alert(errorMessage);
        }
        
        
        
         
        });
      }
    } else {
      alert('Dữ liệu không hợp lệ. Vui lòng kiểm tra lại');
    }
  }
  





  appliedCoupon: any = null; // Biến để lưu thông tin mã giảm giá đã áp dụng

applyCoupon(): void {
  if (!this.couponCode) {
    alert('Vui lòng nhập mã giảm giá.');
    return;
  }

  this.orderService.validateCoupon(this.couponCode, this.totalAmount).subscribe({
    next: (response: any) => {
      console.log('Response từ API:', response);

      // Kiểm tra điều kiện tối thiểu để áp dụng mã giảm giá
      if (response.minOrderValue && this.totalAmount < response.minOrderValue) {
        alert(
          `Mã giảm giá này chỉ áp dụng cho đơn hàng từ ${response.minOrderValue} VNĐ trở lên.`
        );
        return;
      }

      // Lưu mã giảm giá đã áp dụng
      this.appliedCoupon = response;

      // Tính toán giảm giá
      this.discountAmount = this.calculateDiscount(response);
      this.finalAmount = this.totalAmount - this.discountAmount;

      console.log('Số tiền giảm giá:', this.discountAmount);
      console.log('Tổng tiền sau giảm giá:', this.finalAmount);

      // Cập nhật mã giảm giá và tổng tiền
      this.orderData.coupon_code = this.couponCode;
      this.orderData.total_money = this.finalAmount;
    },
    error: (error: any) => {
      console.error('Lỗi khi áp dụng mã giảm giá:', error);
      alert('Mã giảm giá không hợp lệ hoặc đã hết hạn.');
    },
  });
}

  
  

  calculateDiscount(coupon: any): number {
    console.log('Tính giảm giá với coupon:', coupon); // Ghi log để kiểm tra
  
    if (coupon.discountType === 'PERCENTAGE') {
      let discount = (this.totalAmount * coupon.discountValue) / 100;
      if (coupon.maxDiscountValue && discount > coupon.maxDiscountValue) {
        discount = coupon.maxDiscountValue; // Áp dụng giới hạn giảm giá tối đa (nếu có)
      }
      return discount;
    } else if (coupon.discountType === 'FIXED') {
      return coupon.discountValue; // Trả về giá trị giảm cố định
    }
  
    return 0; // Nếu không thuộc loại nào, không giảm giá
  }
  

  
  calculateTotal(): void {
   
    this.totalAmount = this.cartItems.reduce(
      (total, item) => total + (item.product.price * item.quantity), 0
    );
    console.log('Total amount:', this.totalAmount); // Debugging
  }
  removeItem(productId: number): void {
    this.cartService.removeFromCart(productId); // Gọi phương thức xóa sản phẩm
    this.cartItems = this.cartItems.filter(item => item.product.id !== productId); // Cập nhật lại giỏ hàng sau khi xóa sản phẩm
    this.calculateTotal(); // Tính lại tổng tiền sau khi xóa sản phẩm
    if (this.finalAmount < this.discountAmount) {
      this.discountAmount = 0; // Reset giảm giá
      this.orderData.coupon_code = ''; // Xóa mã giảm giá
      alert('Mã giảm giá không còn hợp lệ do giỏ hàng không đáp ứng điều kiện.');
    }
  }
  increaseQuantity(productId: number): void {
    const item = this.cartItems.find(cartItem => cartItem.product.id === productId);
    if (item) {
      item.quantity += 1; // Tăng số lượng
      this.cartService.updateCart(productId, item.quantity); // Cập nhật giỏ hàng
      this.calculateTotal(); // Tính lại tổng tiền
    }
  }

  decreaseQuantity(productId: number): void {
    const item = this.cartItems.find(cartItem => cartItem.product.id === productId);
    if (item) {
      if (item.quantity > 1) {
        item.quantity -= 1; // Giảm số lượng nếu lớn hơn 1
        this.cartService.updateCart(productId, item.quantity); // Cập nhật giỏ hàng
      } else {
        this.removeItem(productId); // Xóa sản phẩm nếu số lượng bằng 1
      }
      this.calculateTotal(); // Tính lại tổng tiền
    }
  }

  updateQuantity(productId: number, event: any): void {
    const value = +event.target.value; // Chuyển giá trị input sang số
    if (!isNaN(value) && value > 0) {
      const item = this.cartItems.find(cartItem => cartItem.product.id === productId);
      if (item) {
        item.quantity = value; // Cập nhật số lượng
        this.cartService.updateCart(productId, item.quantity); // Cập nhật giỏ hàng
        this.calculateTotal(); // Tính lại tổng tiền
        if (this.finalAmount < this.discountAmount) {
          this.discountAmount = 0; // Reset giảm giá
          this.orderData.coupon_code = ''; // Xóa mã giảm giá
          alert('Mã giảm giá không còn hợp lệ do giỏ hàng không đáp ứng điều kiện.');
        }
      }
    }
  }
 

}
