package ie.benjamin.container.scheduler.web.rest;

import ie.benjamin.container.scheduler.ContainerSchedulerApp;
import ie.benjamin.container.scheduler.domain.Container;
import ie.benjamin.container.scheduler.repository.ContainerRepository;
import ie.benjamin.container.scheduler.service.ContainerService;
import ie.benjamin.container.scheduler.service.dto.ContainerDTO;
import ie.benjamin.container.scheduler.service.mapper.ContainerMapper;
import ie.benjamin.container.scheduler.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static ie.benjamin.container.scheduler.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ContainerResource} REST controller.
 */
@SpringBootTest(classes = ContainerSchedulerApp.class)
public class ContainerResourceIT {

    private static final String DEFAULT_CONTAINER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTAINER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_FLAVOUR = "AAAAAAAAAA";
    private static final String UPDATED_FLAVOUR = "BBBBBBBBBB";

    private static final String DEFAULT_OS = "AAAAAAAAAA";
    private static final String UPDATED_OS = "BBBBBBBBBB";

    private static final String DEFAULT_CPU = "AAAAAAAAAA";
    private static final String UPDATED_CPU = "BBBBBBBBBB";

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private ContainerMapper containerMapper;

    @Autowired
    private ContainerService containerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restContainerMockMvc;

    private Container container;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContainerResource containerResource = new ContainerResource(containerService);
        this.restContainerMockMvc = MockMvcBuilders.standaloneSetup(containerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Container createEntity(EntityManager em) {
        Container container = new Container()
            .containerId(DEFAULT_CONTAINER_ID)
            .name(DEFAULT_NAME)
            .image(DEFAULT_IMAGE)
            .status(DEFAULT_STATUS)
            .flavour(DEFAULT_FLAVOUR)
            .os(DEFAULT_OS)
            .cpu(DEFAULT_CPU);
        return container;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Container createUpdatedEntity(EntityManager em) {
        Container container = new Container()
            .containerId(UPDATED_CONTAINER_ID)
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .status(UPDATED_STATUS)
            .flavour(UPDATED_FLAVOUR)
            .os(UPDATED_OS)
            .cpu(UPDATED_CPU);
        return container;
    }

    @BeforeEach
    public void initTest() {
        container = createEntity(em);
    }

    @Test
    @Transactional
    public void createContainer() throws Exception {
        int databaseSizeBeforeCreate = containerRepository.findAll().size();

        // Create the Container
        ContainerDTO containerDTO = containerMapper.toDto(container);
        restContainerMockMvc.perform(post("/api/containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(containerDTO)))
            .andExpect(status().isCreated());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeCreate + 1);
        Container testContainer = containerList.get(containerList.size() - 1);
        assertThat(testContainer.getContainerId()).isEqualTo(DEFAULT_CONTAINER_ID);
        assertThat(testContainer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContainer.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testContainer.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testContainer.getFlavour()).isEqualTo(DEFAULT_FLAVOUR);
        assertThat(testContainer.getOs()).isEqualTo(DEFAULT_OS);
        assertThat(testContainer.getCpu()).isEqualTo(DEFAULT_CPU);
    }

    @Test
    @Transactional
    public void createContainerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = containerRepository.findAll().size();

        // Create the Container with an existing ID
        container.setId(1L);
        ContainerDTO containerDTO = containerMapper.toDto(container);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContainerMockMvc.perform(post("/api/containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkContainerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = containerRepository.findAll().size();
        // set the field null
        container.setContainerId(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc.perform(post("/api/containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = containerRepository.findAll().size();
        // set the field null
        container.setName(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc.perform(post("/api/containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContainers() throws Exception {
        // Initialize the database
        containerRepository.saveAndFlush(container);

        // Get all the containerList
        restContainerMockMvc.perform(get("/api/containers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(container.getId().intValue())))
            .andExpect(jsonPath("$.[*].containerId").value(hasItem(DEFAULT_CONTAINER_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].flavour").value(hasItem(DEFAULT_FLAVOUR.toString())))
            .andExpect(jsonPath("$.[*].os").value(hasItem(DEFAULT_OS.toString())))
            .andExpect(jsonPath("$.[*].cpu").value(hasItem(DEFAULT_CPU.toString())));
    }
    
    @Test
    @Transactional
    public void getContainer() throws Exception {
        // Initialize the database
        containerRepository.saveAndFlush(container);

        // Get the container
        restContainerMockMvc.perform(get("/api/containers/{id}", container.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(container.getId().intValue()))
            .andExpect(jsonPath("$.containerId").value(DEFAULT_CONTAINER_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.flavour").value(DEFAULT_FLAVOUR.toString()))
            .andExpect(jsonPath("$.os").value(DEFAULT_OS.toString()))
            .andExpect(jsonPath("$.cpu").value(DEFAULT_CPU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContainer() throws Exception {
        // Get the container
        restContainerMockMvc.perform(get("/api/containers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContainer() throws Exception {
        // Initialize the database
        containerRepository.saveAndFlush(container);

        int databaseSizeBeforeUpdate = containerRepository.findAll().size();

        // Update the container
        Container updatedContainer = containerRepository.findById(container.getId()).get();
        // Disconnect from session so that the updates on updatedContainer are not directly saved in db
        em.detach(updatedContainer);
        updatedContainer
            .containerId(UPDATED_CONTAINER_ID)
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .status(UPDATED_STATUS)
            .flavour(UPDATED_FLAVOUR)
            .os(UPDATED_OS)
            .cpu(UPDATED_CPU);
        ContainerDTO containerDTO = containerMapper.toDto(updatedContainer);

        restContainerMockMvc.perform(put("/api/containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(containerDTO)))
            .andExpect(status().isOk());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeUpdate);
        Container testContainer = containerList.get(containerList.size() - 1);
        assertThat(testContainer.getContainerId()).isEqualTo(UPDATED_CONTAINER_ID);
        assertThat(testContainer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContainer.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testContainer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testContainer.getFlavour()).isEqualTo(UPDATED_FLAVOUR);
        assertThat(testContainer.getOs()).isEqualTo(UPDATED_OS);
        assertThat(testContainer.getCpu()).isEqualTo(UPDATED_CPU);
    }

    @Test
    @Transactional
    public void updateNonExistingContainer() throws Exception {
        int databaseSizeBeforeUpdate = containerRepository.findAll().size();

        // Create the Container
        ContainerDTO containerDTO = containerMapper.toDto(container);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContainerMockMvc.perform(put("/api/containers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeUpdate);
    }
//
//    @Test
//    @Transactional
    public void deleteContainer() throws Exception {
        // Initialize the database
        containerRepository.saveAndFlush(container);

        int databaseSizeBeforeDelete = containerRepository.findAll().size();

        // Delete the container
        restContainerMockMvc.perform(delete("/api/containers/{id}", container.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Container.class);
        Container container1 = new Container();
        container1.setId(1L);
        Container container2 = new Container();
        container2.setId(container1.getId());
        assertThat(container1).isEqualTo(container2);
        container2.setId(2L);
        assertThat(container1).isNotEqualTo(container2);
        container1.setId(null);
        assertThat(container1).isNotEqualTo(container2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContainerDTO.class);
        ContainerDTO containerDTO1 = new ContainerDTO();
        containerDTO1.setId(1L);
        ContainerDTO containerDTO2 = new ContainerDTO();
        assertThat(containerDTO1).isNotEqualTo(containerDTO2);
        containerDTO2.setId(containerDTO1.getId());
        assertThat(containerDTO1).isEqualTo(containerDTO2);
        containerDTO2.setId(2L);
        assertThat(containerDTO1).isNotEqualTo(containerDTO2);
        containerDTO1.setId(null);
        assertThat(containerDTO1).isNotEqualTo(containerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(containerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(containerMapper.fromId(null)).isNull();
    }
}
