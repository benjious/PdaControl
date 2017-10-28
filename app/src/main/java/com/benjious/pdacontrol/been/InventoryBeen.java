package com.benjious.pdacontrol.been;


/**
 * Created by Benjious on 2017/10/24.
 */

public class InventoryBeen {
    private String pallet_id;
    private String product_id;
    private String product_name;
    private int storeNum;

    public String getPallet_id() {
        return pallet_id;
    }

    public void setPallet_id(String pallet_id) {
        this.pallet_id = pallet_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(int storeNum) {
        this.storeNum = storeNum;
    }

    public InventoryBeen(String pallet_id, String product_id, String product_name, int storeNum) {
        this.pallet_id = pallet_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.storeNum = storeNum;
    }
}
