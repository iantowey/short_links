package com.neueda.url.shortner.repository;


import com.neueda.url.shortner.model.Link;
import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<Link, Integer> {

    Link findByShortPath(String shortLink);

}
