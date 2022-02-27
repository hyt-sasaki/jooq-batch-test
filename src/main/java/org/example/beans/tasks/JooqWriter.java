package org.example.beans.tasks;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("jooqWriter")
public class JooqWriter implements ItemWriter<CityDTO> {
    @Override
    public void write(List<? extends CityDTO> items) {
        System.out.println("=====================================");
        System.out.println();
        items.forEach(System.out::println);
        System.out.println();
        System.out.println("=====================================");
    }
}
