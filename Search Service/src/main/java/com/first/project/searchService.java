package com.first.project;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class searchService {

    @Autowired
    private searchRepository search_repository;

    public boolean checkkey(String key,String search_text){
        List<Searchdb> all= search_repository.findAll();
        //System.out.println("a= "+all.get(0).getKey());
        for (Searchdb s : all){
            if(s.getKey().equals(key)){
                if(s.getSearch_text().equals(search_text)){
                if(s.getSearch_number()<3){
                    return true;
                }
                 return false;
            }
            }
        }
        return true;

    }


    public void addtoKey(String key,String search_text) {
        List<Searchdb> all = search_repository.findAll();
        int number = 1;
        int id = -1;

        for (Searchdb s : all) {
            if (s.getKey().equals(key)) {
                if (s.getSearch_text().equals(search_text)) {
                    if (s.getSearch_number() < 3) {

                        number = s.getSearch_number() + 1;

                    }
                    else {
                    number = 1;
                    }
                    id = s.getId();
                }
            }
        }

        Searchdb searchDB = new Searchdb();
        int x = 0;
        if (id != -1) {
            x = id;
        } else if (all.size() != 0) {
            x = (all.get(all.size() - 1).getId()) + 1;
            searchDB.setId(x);
        }
        //searchDB.setId(1);
        searchDB.setKey(key);
        searchDB.setSearch_text(search_text);
        searchDB.setSearch_number(number);

        searchDB.setId(x);

        if (id!=-1)
        {
            search_repository.myupdate(x,searchDB.getKey(),searchDB.getSearch_number(),searchDB.getSearch_text(),x);
        }
        else
            search_repository.mysave(x, searchDB.getKey(), searchDB.getSearch_number(), searchDB.getSearch_text());

    }
}
