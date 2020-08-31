package com.vapsquare.stock.dbservice.resource;

import com.vapsquare.stock.dbservice.model.Quote;
import com.vapsquare.stock.dbservice.model.Quotes;
import com.vapsquare.stock.dbservice.repository.QuotesRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/db")
public class DBServiceResource {
    /**
     * in this class, we'll ad the CRUD operations for username and quotes.
     */

    private QuotesRepository quotesRepository;

    public DBServiceResource(QuotesRepository quotesRepository) {
        this.quotesRepository = quotesRepository;
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
                .forEach(quote -> quotesRepository.save(quote));

        // we can write above forEach without map like below also.
        /*.forEach(quote -> {
            quoresRepository.save(new Quote(quotes.getUserName(), quote));
        });*/
        return getQuotesByUserName(quotes.getUserName());
    }

    @DeleteMapping("/delete/{username}")
    public List<Quote> delete(@PathVariable("username") final String username){
        List<Quote> quotes = quotesRepository.findByUserName(username);
        quotes.forEach(quote -> quotesRepository.delete(quote));
        return quotes;
    }

    private List<String> getQuotesByUserName(String username) {
        return quotesRepository.findByUserName(username)
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
