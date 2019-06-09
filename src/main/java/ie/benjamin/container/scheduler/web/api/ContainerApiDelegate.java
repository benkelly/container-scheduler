package ie.benjamin.container.scheduler.web.api;

import ie.benjamin.container.scheduler.web.api.model.Container;
import java.util.UUID;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A delegate to be called by the {@link ContainerApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-06-09T15:41:36.809+01:00[Europe/Dublin]")


@Service
public interface ContainerApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * @see ContainerApi#apiDeleteContainer
     */
    default ResponseEntity<Void> apiDeleteContainer(UUID containerId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * @see ContainerApi#apiSchedule
     */
    default ResponseEntity<Container> apiSchedule(Container container) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    ApiUtil.setExampleResponse(request, "application/json", "{  \"image\" : \"image\",  \"scheduler-hints\" : {    \"flavour\" : \"gold\",    \"os\" : \"Linux\",    \"CPU\" : \"AMD\"  },  \"name\" : \"my-super-container\",  \"id\" : \"d290f1ee-6c54-4b01-90e6-d701748f0851\",  \"status\" : \"pending\"}");
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
