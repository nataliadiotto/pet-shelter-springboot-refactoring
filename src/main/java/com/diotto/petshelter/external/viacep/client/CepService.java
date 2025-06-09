package com.diotto.petshelter.external.viacep.client;

import com.diotto.petshelter.external.viacep.dto.ViaCepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface CepService {

    @GetMapping("{cep}/json")
    ViaCepResponse getAddressByCep(@PathVariable("cep") String cep);

}
