package edu.unict.magazzon;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class products {
    @Id
    @GeneratedValue
    private Long id;
    private String name; 
    private Integer quantity;
    private float price;

    public void setId(Long id) {
        this.id=id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setQuantity(int quantity) {
        this.quantity=quantity;
    }

    public int getQuantity() {
        return quantity;
    }
    
    public void setPrice(float price) {
        this.price=price;
    }

    public float getPrice() {
        return price;
    }
    
}
