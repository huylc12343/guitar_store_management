package com.example.g2pedal.Repository;

import com.example.g2pedal.Model.StorageDataModel;

import java.util.ArrayList;

public class StorageDataRepository {
    private static ArrayList<StorageDataModel> storageDataModelList = new ArrayList<>();

    public StorageDataRepository(ArrayList<StorageDataModel> lst) {
        for (StorageDataModel p: lst){
            this.storageDataModelList.add(p);
        }
    }

    public static ArrayList<StorageDataModel> getStorageDataModelList() {
        return storageDataModelList;
    }

    public static void setStorageDataModelList(ArrayList<StorageDataModel> storageDataModelList) {
        StorageDataRepository.storageDataModelList = storageDataModelList;
    }
    public void addStorageDataModelList(StorageDataModel p){
        this.storageDataModelList.add(p);
    }
    public StorageDataModel getProduct(String id){
        StorageDataModel result;
        for ( StorageDataModel p : storageDataModelList) {
            if (id == p.getId())
                return p;
        }
        return  null;
    }
    public void delDB(){
        storageDataModelList.clear();
    }
}
