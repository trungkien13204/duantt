create database thuvien6
go
Use thuvien6
Go
Create table users
(
    id                 int identity(1,1) primary key ,
    fullname           nvarchar(100)          default '',
    phone_number       nvarchar(20) not null,
    address            nvarchar(200)          default '',
    password           nvarchar(100) not null default '',
    created_at         datetime,
    updated_at         datetime,
    active          tinyint               default 1,
    date_of_birth      date,
    facebook_acount_id int                   default 0,
    google_acount_id   int                   default 0,
	role_id int

)
Go
Create table roles
(
    id int primary key,
    name nvarchar(20) not null
)
Go

alter table users
    add foreign key (role_id) references roles (id);

create table tokens
(
    id              int identity(1,1) primary key ,
    token           varchar(255) unique not null,
    token_type      varchar(50)         not null,
    expiration_date datetime,
    revoked         tinyint not null,
    expired         tinyint not null,
    user_id         int,
    foreign key (user_id) references users (id)
);

Go

Create table social_accounts
(
    id         int identity(1,1) primary key ,
    provider   varchar(20)  not null ,
    provide_id varchar(50)  not null,
    email      varchar(150) not null ,
    name       varchar(100) not null,
    user_id    int,
    foreign key (user_id) references users (id)
)
Go

create table categories
(
    id   int identity(1,1) primary key,
    name nvarchar(100) not null default '',
)
Go

Create table product
(
    id          int identity(1,1) primary key,
    name        nvarchar(100),
    price       float not null check (price >= 0),
    thumbnail   nvarchar(300) default '',
    description nvarchar(max)    default '',
    created_at  datetime,
    updated_at  datetime,
    Category_id int,
	author nvarchar(50),
    Foreign key (Category_id) references categories (id)
)
Go
ALTER TABLE product
ADD catalogue NVARCHAR(255); -- Hoặc VARCHAR, tùy theo yêu cầu

ALTER TABLE product
ADD quantity int;


CREATE TABLE Coupon (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,             -- T? ??ng t?ng, khóa chính

    coupon_code NVARCHAR(255) NOT NULL UNIQUE,       -- Mã gi?m giá (không trùng l?p)

    discount_type NVARCHAR(50) NOT NULL,             -- Lo?i gi?m giá (ENUM ???c l?u d??i d?ng chu?i)
    
    discount_value FLOAT NOT NULL,                   -- Giá tr? gi?m giá

    min_order_value FLOAT NULL,                      -- Giá tr? ??n hàng t?i thi?u ?? áp d?ng gi?m giá

    max_discount_value FLOAT NULL,                   -- Giá tr? gi?m giá t?i ?a

    expiry_date DATE NULL,                           -- Ngày h?t h?n

    usage_limit INT NULL,                            -- Gi?i h?n s? l?n s? d?ng

    times_used INT NULL,                             -- S? l?n ?ã s? d?ng

    active BIT NOT NULL                              -- Tr?ng thái kích ho?t (true/false)
)
Go
ALTER TABLE Coupon
ADD CONSTRAINT chk_discount_type
CHECK (discount_type IN ('PERCENTAGE', 'FIXED'));


Create table orders
(
    id           int identity(1,1) primary key,
    user_id      int,
    foreign key (user_id) references users (id),
    fullname     nvarchar(100) default '',
    email        nvarchar(100) default '',
    phone_number nvarchar(20)  not null,
    address      nvarchar(100) not null,
    note         nvarchar(100) default '',
    order_date   datetime     default current_timestamp,
    Status       nvarchar(20),
    total_money  float check (total_money >= 0),
	shipping_method nvarchar(100),
	 shipping_address nvarchar(200),
	 shipping_date date,
	 tracking_number nvarchar(100),
	 payment_method nvarchar(100),
	 active tinyint,
	 coupon_code NVARCHAR(255),                  -- Liên k?t t?i b?ng Coupon
    FOREIGN KEY (coupon_code) REFERENCES Coupon(coupon_code) ON DELETE SET NULL
)
go

ALTER TABLE orders
ADD CONSTRAINT chk_status
CHECK (status IN ('pending', 'processing', 'shipped', 'delivered', 'cancelled'));

Create table order_detail
(
    id                 int identity(1,1) primary key,
    order_id           int,
    foreign key (order_id) references orders (id),
    product_id         int,
    foreign key (product_id) references product (id),
    price              float check (price >= 0),
    number_of_products int check (number_of_products > 0),
    total_money        float check (total_money >= 0),
    color              varchar(20) default ''
)
Go

create table product_images(
       id                 int identity(1,1) primary key ,
       product_id int,
        foreign key (product_id) references product (id),
		image_url nvarchar(300),
constraint fk_product_images_product_id
foreign key (product_id) references product (id) on delete cascade 
);

CREATE TABLE Post (
    id INT IDENTITY(1,1) PRIMARY KEY,         -- T? ??ng t?ng, khóa chính
    name VARCHAR(100) NOT NULL,               -- Tên bài vi?t
    description NVARCHAR(MAX),                -- Mô t? bài vi?t
    author VARCHAR(255),                      -- L?u thông tin tác gi?
    totalLike INT DEFAULT 0,                  -- T?ng s? l??t thích
    created_at DATETIME DEFAULT GETDATE(),    -- Th?i gian t?o
    updated_at DATETIME DEFAULT GETDATE()     -- Th?i gian c?p nh?t (ban ??u)
);
GO

CREATE TRIGGER trg_Post_Update
ON Post
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE Post
    SET updated_at = GETDATE()
    WHERE id IN (SELECT DISTINCT id FROM Inserted);
END;
GO

CREATE TABLE Comment (
    id INT IDENTITY(1,1) PRIMARY KEY,         -- T? ??ng t?ng, khóa chính
    post_id INT NOT NULL,                     -- Liên k?t ??n b?ng Post
    user_id INT NOT NULL,                     -- Liên k?t ??n b?ng User
    content NVARCHAR(MAX) NOT NULL,           -- N?i dung bình lu?n
    created_at DATETIME DEFAULT GETDATE(),    -- Th?i gian t?o
    updated_at DATETIME DEFAULT GETDATE(),    -- Th?i gian c?p nh?t (ban ??u)
    FOREIGN KEY (post_id) REFERENCES Post(id) ON DELETE CASCADE, -- Xóa post s? xóa comment
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE -- Xóa user s? xóa comment
);
GO

CREATE TRIGGER trg_Comment_Update
ON Comment
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE Comment
    SET updated_at = GETDATE()
    WHERE id IN (SELECT DISTINCT id FROM Inserted);
END;
GO







CREATE TABLE CartItems (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,       -- T? ??ng t?ng, khóa chính

    product_id int NOT NULL,                -- Liên k?t t?i b?ng Product
    order_id int NOT NULL,                  -- Liên k?t t?i b?ng Order
    quantity INT,                              -- S? l??ng s?n ph?m

    -- T?o khóa ngo?i liên k?t t?i b?ng Product
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,

    -- T?o khóa ngo?i liên k?t t?i b?ng Order
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
)
Go



INSERT INTO categories (name)
VALUES
(N'ĐỊA'),
(N'TOÁN'),
(N'VĂN'),
(N'LÝ'),
(N'HÓA'),
(N'SỬ'),
(N'TIẾNG ANH'),
(N'TRUYỆN');

Insert into roles
Values(1,'ADMIN'),
(2,'USER');
Insert into users
values(N'Trần Trung Kiên','0974671634',N'Thái Bình','123456','2024/12/25','2024/12/25',1,'2004/03/01',null,null,1)

Select*from users
Select*From product
Select*from categories
Select*from orders

SELECT * FROM users WHERE phone_number = '0339971576';
Select*from Coupon

SELECT DISTINCT discount_type FROM Coupon;
select*from comment