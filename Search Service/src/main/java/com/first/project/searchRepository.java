package com.first.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface searchRepository extends JpaRepository<Searchdb,Integer> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO `searchdb`(`id`, `key`, `search_number`, `search_text`) VALUES (?,?,?,?)",nativeQuery = true)
    public void mysave(int id,String key,int search_number,String search_text);



    @Transactional
    @Modifying
    @Query(value = "UPDATE `searchdb` SET `id`=?,`key`=?,`search_number`=?,`search_text`=? WHERE `id`=?",nativeQuery = true)
    public void myupdate(int id,String key,int search_number,String search_text,int id2);


}
