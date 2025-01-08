//package com.example.backendthuvien.Config;
//
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RoleConfig {
//    // ROLE Definitions
//    @Value("${api.v1.role.admin}")
//    public String ROLE_ADMIN;
//
//    @Value("${api.v1.role.user}")
//    public String ROLE_USER;
//
//    // Orders API
//    @Value("${api.v1.orders.create}")
//    public String ORDERS_CREATE;
//
//    @Value("${api.v1.orders.view}")
//    public String[] ORDERS_VIEW;
//
//    @Value("${api.v1.orders.viewById}")
//    public String[] ORDERS_VIEW_BY_ID;
//
//    @Value("${api.v1.orders.update}")
//    public String ORDERS_UPDATE;
//
//    @Value("${api.v1.orders.delete}")
//    public String ORDERS_DELETE;
//
//    @Value("${api.v1.orders.search}")
//    public String ORDERS_SEARCH;
//
//    // Products API
//    @Value("${api.v1.products.create}")
//    public String PRODUCTS_CREATE;
//
//    @Value("${api.v1.products.update}")
//    public String PRODUCTS_UPDATE;
//
//    @Value("${api.v1.products.delete}")
//    public String PRODUCTS_DELETE;
//
//    @Value("${api.v1.products.view}")
//    public String PRODUCTS_VIEW;
//
//    @Value("${api.v1.products.uploadImages}")
//    public String PRODUCTS_UPLOAD_IMAGES;
//
//    @Value("${api.v1.products.export}")
//    public String PRODUCTS_EXPORT;
//
//    @Value("${api.v1.products.search}")
//    public String PRODUCTS_SEARCH;
//
//    @Value("${api.v1.products.byIds}")
//    public String PRODUCTS_BY_IDS;
//
//    @Value("${api.v1.products.generateFake}")
//    public String PRODUCTS_GENERATE_FAKE;
//
//    @Value("${api.v1.products.images}")
//    public String PRODUCTS_IMAGES;
//
//    // Orders_Details API
//    @Value("${api.v1.ordersDetails.create}")
//    public String ORDERS_DETAILS_CREATE;
//
//    @Value("${api.v1.ordersDetails.view}")
//    public String[] ORDERS_DETAILS_VIEW;
//
//    @Value("${api.v1.ordersDetails.update}")
//    public String ORDERS_DETAILS_UPDATE;
//
//    @Value("${api.v1.ordersDetails.delete}")
//    public String ORDERS_DETAILS_DELETE;
//
//    @Value("${api.v1.ordersDetails.viewByOrder}")
//    public String[] ORDERS_DETAILS_VIEW_BY_ORDER;
//
//    // Categories API
//    @Value("${api.v1.categories.create}")
//    public String CATEGORIES_CREATE;
//
//    @Value("${api.v1.categories.update}")
//    public String CATEGORIES_UPDATE;
//
//    @Value("${api.v1.categories.delete}")
//    public String CATEGORIES_DELETE;
//
//    @Value("${api.v1.categories.view}")
//    public String[] CATEGORIES_VIEW;
//
//    @Value("${api.v1.categories.search}")
//    public String[] CATEGORIES_SEARCH;
//
//    // Coupons API
//    @Value("${api.v1.coupons.create}")
//    public String COUPONS_CREATE;
//
//    @Value("${api.v1.coupons.update}")
//    public String COUPONS_UPDATE;
//
//    @Value("${api.v1.coupons.delete}")
//    public String COUPONS_DELETE;
//
//    @Value("${api.v1.coupons.view}")
//    public String[] COUPONS_VIEW;
//
//    @Value("${api.v1.coupons.validate}")
//    public String[] COUPONS_VALIDATE;
//
//    // User APIs
//    @Value("${api.v1.users.register}")
//    public String USERS_REGISTER;
//
//    @Value("${api.v1.users.login}")
//    public String USERS_LOGIN;
//
//    @Value("${api.v1.users.details}")
//    public String[] USERS_DETAILS;
//
//    @Value("${api.v1.users.update-details}")
//    public String USERS_UPDATE_DETAILS;
//
//    @Value("${api.v1.users.me}")
//    public String USERS_ME;
//
//    @Value("${api.v1.users.get}")
//    public String USERS_GET;
//
//    @Value("${api.v1.users.export}")
//    public String USERS_EXPORT;
//
//    @Value("${api.v1.users.import}")
//    public String USERS_IMPORT;
//
//    // Default Authenticated
//    @Value("${api.v1.default.authenticated}")
//    public String DEFAULT_AUTHENTICATED;
//}
//
