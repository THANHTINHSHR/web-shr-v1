/*
-- Query: SELECT * FROM web_shr_v1.payment_methods
LIMIT 0, 1000

-- Date: 2022-09-07 11:04
*/
INSERT INTO PAYMENT_METHODS  (NAME,DESCRIPTION,IMAGE) VALUES ('COD','Thanh toán tiền mặt khi nhận hàng','/img/payment/cod.png');
INSERT INTO PAYMENT_METHODS  (NAME,DESCRIPTION,IMAGE) VALUES ('Momo','Thanh toán bằng Momo','/img/payment/momo.png');
INSERT INTO PAYMENT_METHODS  (NAME,DESCRIPTION,IMAGE) VALUES ('Direct Bank\r Transfer','Thanh toán qua tài khoản ngân hàng','/img/payment/bank-transfer.png');
INSERT INTO PAYMENT_METHODS  (NAME,DESCRIPTION,IMAGE) VALUES ('Zalo Pay','Thanh toán qua Zalo pay','/img/payment/zalo-pay.png');
