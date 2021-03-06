package cpp.labsuite.megalabs.external.controllers;

import cpp.labsuite.megalabs.domain.ConversionService;
import cpp.labsuite.megalabs.domain.MeasurementConversionServiceFactory;
import cpp.labsuite.megalabs.external.models.ConvertRequest;
import cpp.labsuite.megalabs.external.models.ConvertResponse;
import cpp.labsuite.megalabs.external.models.MeasurementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Component
public class ConverterController {

    @Autowired
    MeasurementConversionServiceFactory conversionServiceFactory;

    @RequestMapping(value = "/converter", method = RequestMethod.GET)
    public String index() {
        return "converter";
    }

    @RequestMapping(value = "/converter/convert",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<ConvertResponse> convert(ConvertRequest request) {
        ConvertResponse response = new ConvertResponse();
        response.setSuccess(true);
        String result = "err";
        try {
            ConversionService service = conversionServiceFactory.getConverter(request.getTargetSystem());
             result = service.convert(request.getRequestValue());
        } catch (NumberFormatException e) {
            response.setSuccess(false);
        }
        response.setResult(result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

