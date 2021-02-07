package com.demo;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.entities.Product;
import com.models.ProductModel;

@SessionScoped
@ManagedBean(name = "productManagedBean")
public class ProductManagedBean {

	private List<Product> products;

	public List<Product> getProducts() {
                ProductModel productModel = new ProductModel();
                this.products = productModel.findAll();
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public ProductManagedBean() {
		ProductModel productModel = new ProductModel();
		this.products = productModel.findAll();
	}

	public String index() {
		ProductModel productModel = new ProductModel();
		this.products = productModel.findAll();
		return "index?faces-redirect=true";
	}

}