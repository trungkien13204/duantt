import { Component, OnInit } from '@angular/core';
import { Product } from '../../responses/user/product';
import { ProductService } from '../../service/product.service';
import { CommonModule } from '@angular/common'; 
import { Category } from '../../responses/user/category';
import { CategoryService } from '../../service/category.service';
import { FormsModule } from '@angular/forms'; 
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { OderComponent } from '../oder/oder.component';
import { HeaderComponent } from '../../header/header.component';
import { AuthGuardFn } from '../../service/Guard/auth.guard';
import { FooterComponent } from '../../footer/footer.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, HeaderComponent, FooterComponent], 
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  products: Product[] = [];
  categories: Category[] = [];
  selectedCategoryId: number = 0;
  currentPage: number = 1;
  itemsPerPage: number = 9;
  totalPages: number = 0;
  visiblePages: number[] = [];
  keyword: string = '';

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private router: Router
  ) {}

  ngOnInit() {
    this.fetchCategories(); // Gọi fetchCategories() thay vì getCategories()
    this.fetchProducts(); // Gọi fetchProducts() để tải sản phẩm
  }

  fetchCategories() {
    this.categoryService.getCategories(1, 100).subscribe({
      next: (response: any) => {
        console.log('Dữ liệu trả về từ API:', response); // Kiểm tra dữ liệu trả về
        if (response && response.data) {
          this.categories = response.data; // Gán danh sách danh mục
        } else {
          console.warn('Không tìm thấy trường "data" trong phản hồi API');
          this.categories = [];
        }
      },
      error: (error: any) => {
        console.error('Lỗi khi lấy danh mục:', error);
        this.categories = [];
      }
    });
  }
  
  
  fetchProducts() {
    const { keyword, selectedCategoryId, currentPage, itemsPerPage } = this;
    this.productService.getProducts(selectedCategoryId, keyword, currentPage - 1, itemsPerPage).subscribe({
      next: (response: any) => {
        if (response && Array.isArray(response.products)) {
          this.products = response.products.map((product: Product) => ({
            ...product,
            url: `http://localhost:8080/api/v1/products/images/${product.thumbnail}`
          }));
          this.totalPages = response.totalPages || 0;
          this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
        } else {
          console.warn('Response does not contain a valid product list:', response);
          this.products = [];
        }
      },
      error: (error: any) => {
        console.error('Error fetching products:', error);
        this.products = [];
        alert('Không thể tải sản phẩm, vui lòng thử lại sau.');
      }
    });
  }
  

  searchProducts() {
    this.currentPage = 1;
    this.fetchProducts();
  }

  filterByCategory(categoryId: number) {
    this.selectedCategoryId = categoryId;
    this.currentPage = 1;
    this.fetchProducts();
  }

  onPageChange(page: number) {
    this.currentPage = page;
    this.fetchProducts();
  }

  generateVisiblePageArray(currentPage: number, totalPages: number): number[] {
    const maxVisiblePages = 5;
    const halfVisiblePages = Math.floor(maxVisiblePages / 2);
    let startPage = Math.max(currentPage - halfVisiblePages, 1);
    let endPage = Math.min(startPage + maxVisiblePages - 1, totalPages);

    if (endPage - startPage + 1 < maxVisiblePages) {
      startPage = Math.max(endPage - maxVisiblePages + 1, 1);
    }

    if (totalPages < maxVisiblePages) {
      startPage = 1;
      endPage = totalPages;
    }

    return Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i);
  }

  onProductClick(productId: number) {
    this.router.navigate(['/products', productId]);
  }

  setupRoutes() {
    this.router.resetConfig([
      {
        path: 'order',
        component: OderComponent,
        canActivate: [AuthGuardFn]
      }
    ]);
  }
}
