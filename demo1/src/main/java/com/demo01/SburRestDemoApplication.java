package com.demo01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class SburRestDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SburRestDemoApplication.class, args);
    }

}

@Entity
class Coffee{
    @Id
    private String id;
    private String name;

    public Coffee() {

    }

    public Coffee(String id, String name) {
        this.id=id;
        this.name=name;
    }

    public Coffee(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public void setId(String id) {
        this.id=id;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

}
@RestController
@RequestMapping("/coffees")
class RestApiDemoController{
    private List<Coffee> coffeeList = new ArrayList<>();

    public RestApiDemoController() {
        coffeeList.add(new Coffee("cafe cereza"));
        coffeeList.add(new Coffee("cafe ganador"));
        coffeeList.add(new Coffee("lareno"));
        coffeeList.add(new Coffee("tres pontas"));
    }



    @GetMapping
    Iterable<Coffee> getCoffees() {
        return coffeeList;
    }

    @GetMapping("/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable String id) {
        for (Coffee c : coffeeList) {
            if (c.getId().equals(id)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    @PostMapping
    Coffee postCoffee(@RequestBody Coffee coffee) {
        coffeeList.add(coffee);
        return coffee;
    }

    @PutMapping("/{id}")
    Coffee putCoffee(@PathVariable String id, @RequestBody Coffee coffee) {
        int coffeeIndex = -1;
        for (Coffee c : coffeeList) {
            if (c.getId().equals(id)) {
                coffeeIndex = coffeeList.indexOf(c);
                coffeeList.set(coffeeIndex, coffee);
            }
        }
        return (coffeeIndex==-1) ? postCoffee(coffee):coffee;
    }

    @DeleteMapping("/{id}")
    void deleteCoffee(@PathVariable String id) {
        coffeeList.removeIf(c -> c.getId().equals(id));
    }



}