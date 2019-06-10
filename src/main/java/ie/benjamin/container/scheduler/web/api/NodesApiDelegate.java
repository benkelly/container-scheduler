package ie.benjamin.container.scheduler.web.api;

import ie.benjamin.container.scheduler.service.NodeService;
import ie.benjamin.container.scheduler.web.api.model.Node;
import java.util.UUID;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
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
 * A delegate to be called by the {@link NodesApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-06-09T15:41:36.809+01:00[Europe/Dublin]")

@Service
public interface NodesApiDelegate {


    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * @see NodesApi#apiGetNode
     */
    default ResponseEntity<Node> apiGetNode(UUID nodeId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    ApiUtil.setExampleResponse(request, "application/json", "{  \"scheduler-hints\" : {    \"flavour\" : \"silver\",    \"os\" : \"Windows\",    \"CPU\" : \"Intel\"  },  \"name\" : \"my-super-node\",  \"id\" : \"d290f1ee-6c54-4b01-90e6-d701748f0851\",  \"capacity\" : {    \"total\" : 10,    \"used\" : 4  }}");
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * @see NodesApi#apiGetNodes
     */
    default ResponseEntity<List<Node>> apiGetNodes() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    ApiUtil.setExampleResponse(request, "application/json", "{  \"scheduler-hints\" : {    \"flavour\" : \"silver\",    \"os\" : \"Windows\",    \"CPU\" : \"Intel\"  },  \"name\" : \"my-super-node\",  \"id\" : \"d290f1ee-6c54-4b01-90e6-d701748f0851\",  \"capacity\" : {    \"total\" : 10,    \"used\" : 4  }}");
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
