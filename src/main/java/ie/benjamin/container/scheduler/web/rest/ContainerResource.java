package ie.benjamin.container.scheduler.web.rest;

import ie.benjamin.container.scheduler.service.ContainerService;
import ie.benjamin.container.scheduler.service.NodeService;
import ie.benjamin.container.scheduler.service.dto.NodeDTO;
import ie.benjamin.container.scheduler.web.api.model.Container;
import ie.benjamin.container.scheduler.web.rest.errors.BadRequestAlertException;
import ie.benjamin.container.scheduler.service.dto.ContainerDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ie.benjamin.container.scheduler.domain.Container}.
 */
@RestController
@RequestMapping("/api")
public class ContainerResource {

    private final Logger log = LoggerFactory.getLogger(ContainerResource.class);

    private static final String ENTITY_NAME = "container";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContainerService containerService;

    @Autowired
    private NodeService nodeService;

    public ContainerResource(ContainerService containerService) {
        this.containerService = containerService;
    }

    /**
     * {@code POST  /containers} : Create a new container.
     *
     * @param containerDTO the containerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new containerDTO, or with status {@code 400 (Bad Request)} if the container has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/containers")
    public ResponseEntity<ContainerDTO> createContainer(@Valid @RequestBody ContainerDTO containerDTO) throws URISyntaxException {
        log.debug("REST request to save Container : {}", containerDTO);
        if (containerDTO.getId() != null) {
            throw new BadRequestAlertException("A new container cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContainerDTO result = containerService.save(containerDTO);
        return ResponseEntity.created(new URI("/api/containers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /containers} : Updates an existing container.
     *
     * @param containerDTO the containerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated containerDTO,
     * or with status {@code 400 (Bad Request)} if the containerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the containerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/containers")
    public ResponseEntity<ContainerDTO> updateContainer(@Valid @RequestBody ContainerDTO containerDTO) throws URISyntaxException {
        log.debug("REST request to update Container : {}", containerDTO);
        if (containerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContainerDTO result = containerService.save(containerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, containerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /containers} : get all the containers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of containers in body.
     */
    @GetMapping("/containers")
    public ResponseEntity<List<ContainerDTO>> getAllContainers(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Containers");
        Page<ContainerDTO> page = containerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /containers/:id} : get the "id" container.
     *
     * @param id the id of the containerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the containerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/containers/{id}")
    public ResponseEntity<ContainerDTO> getContainer(@PathVariable Long id) {
        log.debug("REST request to get Container : {}", id);
        Optional<ContainerDTO> containerDTO = containerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(containerDTO);
    }

    /**
     * {@code DELETE  /containers/:id} : delete the "id" container.
     *
     * @param id the id of the containerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/containers/{id}")
    public ResponseEntity<Void> deleteContainer(@PathVariable Long id) {
        log.debug("REST request to delete Container : {}", id);
        containerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }


    @ApiOperation(value = "schedules a container", nickname = "apiSchedule", notes = "select an appropriate node to host the container ", response = Container.class, tags = {})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "container is scheduled to a node", response = Container.class),
        @ApiResponse(code = 400, message = "bad input parameter"),
        @ApiResponse(code = 404, message = "no node could be found to schedule to container too")})
    @RequestMapping(value = "/container/schedule",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    public ResponseEntity<String> apiSchedule(@ApiParam(value = "Container to schedule") @Valid @RequestBody Container container) {

        if (container != null) {
            log.debug("REST request to apiSchedule Container : {}", container);
            ContainerDTO containerDTO = convertToContainerDTO(container);
            log.debug("containerDTO : {}", containerDTO);


            Page<NodeDTO> nodeList = nodeService.findAll(new PageRequest(
                0,
                Integer.MAX_VALUE,
                Sort.Direction.ASC, "id"));


            NodeDTO bestNode = null;
            for (NodeDTO nodeDTO : nodeList) {
                if (nodeDTO.getCpu().equalsIgnoreCase(containerDTO.getCpu()) &&
                    nodeDTO.getFlavour().equalsIgnoreCase(containerDTO.getFlavour()) &&
                    nodeDTO.getOs().equalsIgnoreCase(containerDTO.getOs())) {

                    if (bestNode == null) {
                        if (nodeDTO.getTotalCapacity() - nodeDTO.getUsedCapacity() > 0) {
                            bestNode = nodeDTO;
                        }
                    }

                    if (bestNode != null) {
                        if (bestNode.getTotalCapacity() - bestNode.getUsedCapacity() >
                            nodeDTO.getTotalCapacity() - nodeDTO.getUsedCapacity()) {
                            if (nodeDTO.getTotalCapacity() - nodeDTO.getUsedCapacity() > 0) {
                                bestNode = nodeDTO;
                            }
                        }
                    }
                }
            }
            if (bestNode != null) {
                containerDTO.setNodeId(bestNode.getId());
                containerDTO.setNodeName(bestNode.getName());
                containerService.save(containerDTO);
                bestNode.setUsedCapacity(bestNode.getUsedCapacity()+1);
                nodeService.save(bestNode);
                return apiSchedule(container);
            }
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("No similar Nodes for container");
        }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("No validcontainer");
    }

    private ContainerDTO convertToContainerDTO(@RequestBody @ApiParam("Container to schedule") @Valid Container container) {
        ContainerDTO containerDTO = new ContainerDTO();
        containerDTO.setContainerId(String.valueOf(container.getId()));
        containerDTO.setId((long) container.getId().hashCode());
        containerDTO.setName(container.getName());
        containerDTO.setCpu(container.getSchedulerHints().get("CPU"));
        containerDTO.setFlavour(container.getSchedulerHints().get("flavour"));
        containerDTO.setOs(container.getSchedulerHints().get("os"));
        containerDTO.setImage(container.getImage());
        return containerDTO;
    }


}
