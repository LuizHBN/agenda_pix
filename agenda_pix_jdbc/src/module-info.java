/**
 * 
 */
/**
 * @author Luiz Henrique
 *
 */
module agenda_pix_jdbc {
	requires javafx.controls;
	requires java.sql;
	
	opens view;
	opens controller;
	opens model to javafx.base;

    exports model;
	
}