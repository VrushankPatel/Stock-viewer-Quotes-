package com.vapsquare.stock.dbservice.resource;

import com.vapsquare.stock.dbservice.model.Quote;
import com.vapsquare.stock.dbservice.model.Quotes;
import com.vapsquare.stock.dbservice.repository.QuotesRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/db")
public class DBServiceResource {


    private QuotesRepository quoresRepository;

    // below endpoint is for getting the quotes for a user
    @GetMapping("/{username}")
    public List<String> getQuotes(@PathVariable("username")
                                      final String username){

        return quoresRepository.findByUserName(username)
                .stream()
                .map(Quote::getQuote)
                .collect(Collectors.toList());
        /*
        in above statement, we can write the map function like this
            .map(quote -> {
                return quote.getQuote();
            })
            // OR like this
            .map(quote -> quote.getQuote())
         */
    }

    // now, this endpoint is for add the user
    @PostMapping("/add")
    public List<String> add(@RequestBody final Quotes quotes){
        return null;
    }
}
