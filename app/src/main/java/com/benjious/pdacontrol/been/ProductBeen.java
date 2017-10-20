package com.benjious.pdacontrol.been;

import java.util.Date;

/**
 * Created by Benjious on 2017/10/19.
 */

public class ProductBeen {
    private int productNum;
    private String productName;
    private String productId;
    private Date productDate;

    public ProductBeen(int productNum, String productName, String productId, Date productDate) {
        this.productNum = productNum;
        this.productName = productName;
        this.productId = productId;
        this.productDate = productDate;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }
}
