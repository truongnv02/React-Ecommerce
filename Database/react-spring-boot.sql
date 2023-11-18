use master
go
IF EXISTS ( SELECT name
				FROM sys.databases
				WHERE name = N'react_spring_boot' )
	DROP DATABASE react_spring_boot
create database react_spring_boot
go
use react_spring_boot
go

create table Roles(
	[id] int primary key identity,
	[name] nvarchar(20) not null
);
go

create table Accounts(
	[id] int primary key identity,
	[full_name] nvarchar(100) default '',
	[address] nvarchar(100) default '',
	[phone_number] nvarchar(15),
	[email] nvarchar(100) default '',
	[password] nvarchar(100) not null,
	[is_active] bit default 1,
	[google_account_id] int default 0,
	[role_id] int,
	foreign key (role_id) references Roles(id)
);
go

create table Brands(
	[id] int primary key identity,
	[name] nvarchar(100) not null default ''
);
go

create table Categories(
	[id] int primary key identity,
	[name] nvarchar(100) not null default ''
);
go

create table Products(
	[id] int primary key identity,
	[name] nvarchar(100) not null default '',
	[brand_id] int,
	[category_id] int,
	foreign key (brand_id) references Brands(id),
	foreign key (category_id) references Categories(id)
);
go

create table Colors(
	[id] int primary key identity,
	[name] nvarchar(100) not null default ''
);
go

create table Sizes(
	[id] int primary key identity,
	[value] int not null
);
go

create table Product_detail(
	[id] int primary key identity,
	[product_id] int,
	[color_id] int,
	[size_id] int,
	[price] float check(price >= 0),
	[quantity] int,
	[description] nvarchar(255) default '',
	[image_url] nvarchar(200) default '',
	foreign key (product_id) references Products(id),
	foreign key (color_id) references Colors(id),
	foreign key (size_id) references Sizes(id)
);
go

create table ProductImages(
	[id] int primary key identity,
	[product_detail_id] int,
	[image_url] varchar(255) not null,
	foreign key (product_detail_id) references Product_detail(id) on delete cascade
);
go

create table Orders(
	[id] int primary key identity,
	[account_id] int,
	[full_name] varchar(100) default '',
	[phone_number] varchar(20) NOT NULL,
	[address] varchar(200) NOT NULL, -- Địa chỉ nơi gửi
	[order_date] datetime default current_timestamp,
	[status] varchar(20), -- Trạng thái đơn hàng
	[total_money] float check(total_money >= 0), -- Tổng tiền
	[is_active] bit,
	[payment_method] varchar(100), -- Phương thức thanh toán
	foreign key (account_id) references Accounts(id)
);
go

create table Order_detail(
	[id] int primary key identity,
	[order_id] int,
	[product_detail_id] int,
	[price] float check(price >= 0),
	[quantity] int check(quantity > 0),
	[total_money] float check(total_money >= 0),
	foreign key (order_id) references Orders(id),
	foreign key (product_detail_id) references Product_detail(id),
);
go