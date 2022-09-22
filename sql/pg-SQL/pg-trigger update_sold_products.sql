CREATE OR REPLACE FUNCTION update_sold_products() RETURNS trigger AS $update_sold_products$
	DECLARE
			CURRENT_SALE_NUMBER INT;
			CURRENT_SELL_NUMBER INT;
	 BEGIN
	if NOT EXISTS (SELECT * FROM SOLD_PRODUCTS WHERE SOLD_PRODUCTS.PRODUCT_ID = NEW.Product_id) then
	 CURRENT_SALE_NUMBER  = (SELECT SALE_NUMBER FROM SOLD_PRODUCTS WHERE PRODUCT_ID = NEW.Product_id)+1;
	 CURRENT_SELL_NUMBER  = (SELECT SELL_NUMBER FROM SOLD_PRODUCTS WHERE PRODUCT_ID = NEW.Product_id)+ New.Quantity;
	UPDATE sold_products SET SALE_NUMBER = CURRENT_SALE_NUMBER, SELL_NUMBER = CURRENT_SELL_NUMBER  WHERE PRODUCT_ID = NEW.Product_id;
	RETURN NEW;
	ELSE
	INSERT INTO SOLD_PRODUCTS ( PRODUCT_ID, SALE_NUMBER, SELL_NUMBER ) VALUES( NEW.Product_id, 1, New.Quantity);
    RETURN NEW;
	END IF;
	END;
$update_sold_products$ LANGUAGE plpgsql;
CREATE TRIGGER update_sold_products AFTER INSERT ON ORDER_ITEMS
	FOR EACH ROW EXECUTE FUNCTION update_sold_products() ;










