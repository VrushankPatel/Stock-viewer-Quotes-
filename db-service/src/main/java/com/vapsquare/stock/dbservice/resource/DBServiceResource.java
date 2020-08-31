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

    public DBServiceResource(QuotesRepository quoresRepository) {
        this.quoresRepository = quoresRepository;
    }

    // below endpoint is for getting the quotes for a user
    @GetMapping("/{username}")
    public List<String> getQuotes(@PathVariable("username")
                                      final String username){

        return getQuotesByUserName(username);
    }

    // now, this endpoint is for add the user and quotes
    @PostMapping("/add")
    public List<String> add(@RequestBody final Quotes quotes){

        quotes.getQuotes()
                .stream()
                .map(quote -> new Quote(quotes.getUserName(), quote))
                .forEach(quote -> quoresRepository.save(quote));

        // we can write above forEach without map like below also.
        /*.forEach(quote -> {
            quoresRepository.save(new Quote(quotes.getUserName(), quote));
        });*/
        return getQuotesByUserName(quotes.getUserName());
    }

    private List<String> getQuotesByUserName(String username) {
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
}
