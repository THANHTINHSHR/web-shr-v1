/*
-- Query: SELECT * FROM web_shr_v1.shipping_methods
LIMIT 0, 1000

-- Date: 2022-09-07 10:42
*/
INSERT INTO shipping_methods  (NAME,DESCRIPTION,SHIPPING_COST,EARLIEST_DELIVERY_DAY,LATEST_DELIVERY_DAY,IMAGE) VALUES ('GHTK','Giao hàng tiết kiệm',50000,5,10,'/img/shipping/ghtk.png');
INSERT INTO shipping_methods  (NAME,DESCRIPTION,SHIPPING_COST,EARLIEST_DELIVERY_DAY,LATEST_DELIVERY_DAY,IMAGE) VALUES ('Viettel Post','Viettel post',75000,3,7,'/img/shipping/viettel-post.jpg');
INSERT INTO shipping_methods  (NAME,DESCRIPTION,SHIPPING_COST,EARLIEST_DELIVERY_DAY,LATEST_DELIVERY_DAY,IMAGE) VALUES ('Express\r Delivery','Express\r Delivery',100000,1,3,'/img/shipping/express-delivery.jpg');