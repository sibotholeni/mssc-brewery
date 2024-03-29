package guru.springframework.msscbrewery.web.controller;

import guru.springframework.msscbrewery.web.model.BeerDto;
import guru.springframework.msscbrewery.web.services.BeerService;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

  private final BeerService beerService;

  public BeerController(BeerService beerService) {
    this.beerService = beerService;
  }

  @GetMapping({"/{beerId}"})
  public ResponseEntity<BeerDto> getBeer(@PathVariable("beerId") UUID beerId) {
    return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<HttpHeaders> handlePost(@Valid @RequestBody BeerDto beerDto) {
    BeerDto savedBeer = beerService.saveNewBeer(beerDto);

    HttpHeaders httpHeaders = new HttpHeaders();
    // TODO add hostname to url
    httpHeaders.add("Location", "/api/v1/beer" + savedBeer.getId().toString());

    return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
  }

  @PutMapping({"/{beerId}"})
  public ResponseEntity<HttpStatus> handleUpdate(@PathVariable("beerId") UUID beerId, @Valid @RequestBody BeerDto beerDto) {
    beerService.updateBeer(beerId, beerDto);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping({"/{beerId}"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteBeer(@PathVariable("beerId") UUID beerId) {
    beerService.deleteById(beerId);
  }
}
