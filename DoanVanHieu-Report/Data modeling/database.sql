CREATE TABLE aims.user (
  UserId char(36) NOT NULL DEFAULT '',
  UserName varchar(255) DEFAULT NULL,
  Password varchar(50) DEFAULT NULL,
  Emial varchar(255) DEFAULT NULL,
  PhoneNumber varchar(50) DEFAULT NULL,
  PRIMARY KEY (UserId)
)
ENGINE = INNODB,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

CREATE TABLE aims.cart (
  CartId char(36) NOT NULL DEFAULT '',
  UserId char(36) DEFAULT NULL,
  TotalAmount decimal(19, 2) DEFAULT NULL,
  PRIMARY KEY (CartId)
)
ENGINE = INNODB,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

ALTER TABLE aims.cart
ADD CONSTRAINT FK_cart_UserId FOREIGN KEY (UserId)
REFERENCES aims.user (UserId) ON DELETE NO ACTION;

CREATE TABLE aims.media (
  MediaId char(36) NOT NULL DEFAULT '',
  Category int(11) DEFAULT NULL,
  Price decimal(19, 2) DEFAULT NULL,
  Quantity int(11) DEFAULT NULL,
  Title varchar(255) DEFAULT NULL,
  PRIMARY KEY (MediaId)
)
ENGINE = INNODB,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

CREATE TABLE aims.dvd (
  DVDId char(36) NOT NULL DEFAULT '',
  MediaId char(36) DEFAULT NULL,
  DiscType int(11) DEFAULT NULL,
  Director varchar(255) DEFAULT NULL,
  Studio varchar(255) DEFAULT NULL,
  SubTitle varchar(255) DEFAULT NULL,
  PRIMARY KEY (DVDId)
)
ENGINE = INNODB,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

ALTER TABLE aims.dvd
ADD CONSTRAINT FK_dvd_MediaId FOREIGN KEY (MediaId)
REFERENCES aims.media (MediaId) ON DELETE NO ACTION;

CREATE TABLE aims.book (
  BookId char(36) NOT NULL DEFAULT '',
  MediaId varchar(255) DEFAULT NULL,
  Publisher varchar(255) DEFAULT NULL,
  Author varchar(255) DEFAULT NULL,
  Language int(11) DEFAULT NULL,
  BookCategory varchar(255) DEFAULT NULL,
  PublisherDate timestamp NULL DEFAULT NULL,
  NumberOfPage int(11) DEFAULT NULL,
  PRIMARY KEY (BookId)
)
ENGINE = INNODB,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

ALTER TABLE aims.book
ADD CONSTRAINT FK_book_MediaId FOREIGN KEY (MediaId)
REFERENCES aims.media (MediaId) ON DELETE NO ACTION;

CREATE TABLE aims.mediacart (
  MediaCardId char(36) NOT NULL DEFAULT '',
  CardId char(36) DEFAULT NULL,
  MediaId char(36) DEFAULT NULL,
  PRIMARY KEY (MediaCardId)
)
ENGINE = INNODB,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

ALTER TABLE aims.mediacart
ADD CONSTRAINT FK_mediacart_CardId FOREIGN KEY (CardId)
REFERENCES aims.cart (CartId) ON DELETE NO ACTION;

ALTER TABLE aims.mediacart
ADD CONSTRAINT FK_mediacart_MediaId FOREIGN KEY (MediaId)
REFERENCES aims.media (MediaId) ON DELETE NO ACTION;

CREATE TABLE aims.deliveryinfo (
  DeliveryInfoId char(36) NOT NULL DEFAULT '',
  Name varchar(50) DEFAULT NULL,
  Address varchar(255) DEFAULT NULL,
  Instructions varchar(255) DEFAULT NULL,
  Province varchar(255) DEFAULT NULL,
  PRIMARY KEY (DeliveryInfoId)
)
ENGINE = INNODB,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

CREATE TABLE aims.`order` (
  OrderId char(36) NOT NULL DEFAULT '',
  DeliveryInfoId varchar(255) DEFAULT NULL,
  Price decimal(19, 2) DEFAULT NULL,
  Quantity int(11) DEFAULT NULL,
  ShippingFee decimal(19, 2) DEFAULT NULL,
  OrderTime timestamp NULL DEFAULT NULL,
  PRIMARY KEY (OrderId)
)
ENGINE = INNODB,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

ALTER TABLE aims.`order`
ADD CONSTRAINT FK_order_DeliveryInfoId FOREIGN KEY (DeliveryInfoId)
REFERENCES aims.deliveryinfo (DeliveryInfoId) ON DELETE NO ACTION;

CREATE TABLE aims.mediaorder (
  MediaOrderId char(36) NOT NULL DEFAULT '',
  MediaId char(36) DEFAULT NULL,
  OrderId varchar(255) DEFAULT NULL,
  PRIMARY KEY (MediaOrderId)
)
ENGINE = INNODB,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

ALTER TABLE aims.mediaorder
ADD CONSTRAINT FK_mediaorder_MediaId FOREIGN KEY (MediaId)
REFERENCES aims.media (MediaId) ON DELETE NO ACTION;

ALTER TABLE aims.mediaorder
ADD CONSTRAINT FK_mediaorder_OrderId FOREIGN KEY (OrderId)
REFERENCES aims.`order` (OrderId) ON DELETE NO ACTION;

CREATE TABLE aims.invoice (
  InvoiceId char(36) NOT NULL DEFAULT '',
  OrderId varchar(255) DEFAULT NULL,
  Total decimal(19, 2) DEFAULT NULL,
  Content varchar(255) DEFAULT NULL,
  PRIMARY KEY (InvoiceId)
)
ENGINE = INNODB,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

ALTER TABLE aims.invoice
ADD CONSTRAINT FK_invoice_OrderId FOREIGN KEY (OrderId)
REFERENCES aims.`order` (OrderId) ON DELETE NO ACTION;

CREATE TABLE aims.creditcard (
  CreditCardId char(36) NOT NULL DEFAULT '',
  Name varchar(50) DEFAULT NULL,
  Number int(11) DEFAULT NULL,
  SecurityCode int(3) DEFAULT NULL,
  ExpirationDate varchar(255) DEFAULT NULL,
  PRIMARY KEY (CreditCardId)
)
ENGINE = INNODB,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

CREATE TABLE aims.transaction (
  TransactionId char(36) NOT NULL DEFAULT '',
  InvoiceId varchar(255) DEFAULT NULL,
  CreaditCardId char(36) DEFAULT NULL,
  Content varchar(255) DEFAULT NULL,
  Method varchar(255) DEFAULT NULL,
  CreateDate timestamp NULL DEFAULT NULL,
  PRIMARY KEY (TransactionId)
)
ENGINE = INNODB,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

ALTER TABLE aims.transaction
ADD CONSTRAINT FK_transaction_CreaditCardId FOREIGN KEY (CreaditCardId)
REFERENCES aims.creditcard (CreditCardId) ON DELETE NO ACTION;

ALTER TABLE aims.transaction
ADD CONSTRAINT FK_transaction_InvoiceId FOREIGN KEY (InvoiceId)
REFERENCES aims.invoice (InvoiceId) ON DELETE NO ACTION;