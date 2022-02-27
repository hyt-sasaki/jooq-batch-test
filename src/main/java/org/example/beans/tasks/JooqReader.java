package org.example.beans.tasks;

import org.jooq.DSLContext;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.stream.Stream;

import static org.example.generated.sakila.Tables.CITY;
import static org.example.generated.sakila.Tables.COUNTRY;

@Component("jooqReader")
@StepScope
public class JooqReader implements ItemReader<CityDTO> {

    private final DSLContext jooq;

    private final Iterator<CityDTO> cursor;

    @Autowired
    JooqReader(DSLContext dslContext) {
        this.jooq = dslContext;
        this.cursor = openCursor().iterator();
    }

    @Override
    public CityDTO read() {
        if (this.cursor.hasNext()) {
            return this.cursor.next();
        }
        return null;
    }

    private Stream<CityDTO> openCursor() {
        return jooq.select(CITY.CITY_ID, COUNTRY.COUNTRY_, CITY.CITY_)
                .from(CITY)
                .join(COUNTRY).on(CITY.COUNTRY_ID.eq(COUNTRY.COUNTRY_ID))
                .fetchStream()
                .map(r -> CityDTO.builder()
                        .cityId(r.get(CITY.CITY_ID).shortValue())
                        .countryName(r.get(COUNTRY.COUNTRY_))
                        .cityName(r.get(CITY.CITY_))
                        .build());
    }

}
