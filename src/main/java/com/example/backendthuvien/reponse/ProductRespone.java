package com.example.backendthuvien.reponse;

import com.example.backendthuvien.entity.Categories;
import com.example.backendthuvien.entity.Product;
import com.example.backendthuvien.entity.ProductImage;
import com.example.backendthuvien.entity.baseEnity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class ProductRespone extends baseEntityRespone {


private Long id;
    private String name;

    private Float price;


    private String thumbnail;


    private String description;
    @Column(name = "catalogue")
    private String catalogue;
    private String 	author;
    @JsonProperty("product_images")
private List<ProductImage> productImages=new ArrayList<>();

    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("category_id")
    private Long cateforyId;

    public static ProductRespone fromProduct(Product product){
        ProductRespone productRespone= ProductRespone.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .author(product.getAuthor())
                .catalogue(product.getCatalogue())
                .cateforyId(product.getCategories().getId())
                .productImages(product.getProductImages())

                .build();

        if (product.getQuantity() != null && product.getQuantity() == 0) {
            productRespone.setQuantity("Hết hàng");  // Hiển thị "Hết hàng"
        } else if (product.getQuantity() != null) {
            productRespone.setQuantity(String.valueOf(product.getQuantity()));  // Hiển thị số lượng
        } else {
            productRespone.setQuantity("Số lượng không xác định");  // Trường hợp quantity là null
        }
        productRespone.setCreatedAt(product.getCreatedAt());
        productRespone.setUpdatedAt(product.getUpdatedAt());
        return productRespone;

    }
}
