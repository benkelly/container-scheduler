package ie.benjamin.container.scheduler.web.rest;

import ie.benjamin.container.scheduler.ContainerSchedulerApp;
import ie.benjamin.container.scheduler.domain.Node;
import ie.benjamin.container.scheduler.repository.NodeRepository;
import ie.benjamin.container.scheduler.service.NodeService;
import ie.benjamin.container.scheduler.service.dto.NodeDTO;
import ie.benjamin.container.scheduler.service.mapper.NodeMapper;
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
 * Integration tests for the {@Link NodeResource} REST controller.
 */
@SpringBootTest(classes = ContainerSchedulerApp.class)
public class NodeResourceIT {

    private static final String DEFAULT_NODE_ID = "AAAAAAAAAA";
    private static final String UPDATED_NODE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL_CAPACITY = 1;
    private static final Integer UPDATED_TOTAL_CAPACITY = 2;

    private static final Integer DEFAULT_USED_CAPACITY = 1;
    private static final Integer UPDATED_USED_CAPACITY = 2;

    private static final String DEFAULT_FLAVOUR = "AAAAAAAAAA";
    private static final String UPDATED_FLAVOUR = "BBBBBBBBBB";

    private static final String DEFAULT_OS = "AAAAAAAAAA";
    private static final String UPDATED_OS = "BBBBBBBBBB";

    private static final String DEFAULT_CPU = "AAAAAAAAAA";
    private static final String UPDATED_CPU = "BBBBBBBBBB";

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private NodeService nodeService;

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

    private MockMvc restNodeMockMvc;

    private Node node;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NodeResource nodeResource = new NodeResource(nodeService);
        this.restNodeMockMvc = MockMvcBuilders.standaloneSetup(nodeResource)
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
    public static Node createEntity(EntityManager em) {
        Node node = new Node()
            .nodeId(DEFAULT_NODE_ID)
            .name(DEFAULT_NAME)
            .totalCapacity(DEFAULT_TOTAL_CAPACITY)
            .usedCapacity(DEFAULT_USED_CAPACITY)
            .flavour(DEFAULT_FLAVOUR)
            .os(DEFAULT_OS)
            .cpu(DEFAULT_CPU);
        return node;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Node createUpdatedEntity(EntityManager em) {
        Node node = new Node()
            .nodeId(UPDATED_NODE_ID)
            .name(UPDATED_NAME)
            .totalCapacity(UPDATED_TOTAL_CAPACITY)
            .usedCapacity(UPDATED_USED_CAPACITY)
            .flavour(UPDATED_FLAVOUR)
            .os(UPDATED_OS)
            .cpu(UPDATED_CPU);
        return node;
    }

    @BeforeEach
    public void initTest() {
        node = createEntity(em);
    }

    @Test
    @Transactional
    public void createNode() throws Exception {
        int databaseSizeBeforeCreate = nodeRepository.findAll().size();

        // Create the Node
        NodeDTO nodeDTO = nodeMapper.toDto(node);
        restNodeMockMvc.perform(post("/api/nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeDTO)))
            .andExpect(status().isCreated());

        // Validate the Node in the database
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeCreate + 1);
        Node testNode = nodeList.get(nodeList.size() - 1);
        assertThat(testNode.getNodeId()).isEqualTo(DEFAULT_NODE_ID);
        assertThat(testNode.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNode.getTotalCapacity()).isEqualTo(DEFAULT_TOTAL_CAPACITY);
        assertThat(testNode.getUsedCapacity()).isEqualTo(DEFAULT_USED_CAPACITY);
        assertThat(testNode.getFlavour()).isEqualTo(DEFAULT_FLAVOUR);
        assertThat(testNode.getOs()).isEqualTo(DEFAULT_OS);
        assertThat(testNode.getCpu()).isEqualTo(DEFAULT_CPU);
    }

    @Test
    @Transactional
    public void createNodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nodeRepository.findAll().size();

        // Create the Node with an existing ID
        node.setId(1L);
        NodeDTO nodeDTO = nodeMapper.toDto(node);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNodeMockMvc.perform(post("/api/nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Node in the database
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNodeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = nodeRepository.findAll().size();
        // set the field null
        node.setNodeId(null);

        // Create the Node, which fails.
        NodeDTO nodeDTO = nodeMapper.toDto(node);

        restNodeMockMvc.perform(post("/api/nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeDTO)))
            .andExpect(status().isBadRequest());

        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nodeRepository.findAll().size();
        // set the field null
        node.setName(null);

        // Create the Node, which fails.
        NodeDTO nodeDTO = nodeMapper.toDto(node);

        restNodeMockMvc.perform(post("/api/nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeDTO)))
            .andExpect(status().isBadRequest());

        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNodes() throws Exception {
        // Initialize the database
        nodeRepository.saveAndFlush(node);

        // Get all the nodeList
        restNodeMockMvc.perform(get("/api/nodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(node.getId().intValue())))
            .andExpect(jsonPath("$.[*].nodeId").value(hasItem(DEFAULT_NODE_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].totalCapacity").value(hasItem(DEFAULT_TOTAL_CAPACITY)))
            .andExpect(jsonPath("$.[*].usedCapacity").value(hasItem(DEFAULT_USED_CAPACITY)))
            .andExpect(jsonPath("$.[*].flavour").value(hasItem(DEFAULT_FLAVOUR.toString())))
            .andExpect(jsonPath("$.[*].os").value(hasItem(DEFAULT_OS.toString())))
            .andExpect(jsonPath("$.[*].cpu").value(hasItem(DEFAULT_CPU.toString())));
    }
    
    @Test
    @Transactional
    public void getNode() throws Exception {
        // Initialize the database
        nodeRepository.saveAndFlush(node);

        // Get the node
        restNodeMockMvc.perform(get("/api/nodes/{id}", node.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(node.getId().intValue()))
            .andExpect(jsonPath("$.nodeId").value(DEFAULT_NODE_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.totalCapacity").value(DEFAULT_TOTAL_CAPACITY))
            .andExpect(jsonPath("$.usedCapacity").value(DEFAULT_USED_CAPACITY))
            .andExpect(jsonPath("$.flavour").value(DEFAULT_FLAVOUR.toString()))
            .andExpect(jsonPath("$.os").value(DEFAULT_OS.toString()))
            .andExpect(jsonPath("$.cpu").value(DEFAULT_CPU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNode() throws Exception {
        // Get the node
        restNodeMockMvc.perform(get("/api/nodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNode() throws Exception {
        // Initialize the database
        nodeRepository.saveAndFlush(node);

        int databaseSizeBeforeUpdate = nodeRepository.findAll().size();

        // Update the node
        Node updatedNode = nodeRepository.findById(node.getId()).get();
        // Disconnect from session so that the updates on updatedNode are not directly saved in db
        em.detach(updatedNode);
        updatedNode
            .nodeId(UPDATED_NODE_ID)
            .name(UPDATED_NAME)
            .totalCapacity(UPDATED_TOTAL_CAPACITY)
            .usedCapacity(UPDATED_USED_CAPACITY)
            .flavour(UPDATED_FLAVOUR)
            .os(UPDATED_OS)
            .cpu(UPDATED_CPU);
        NodeDTO nodeDTO = nodeMapper.toDto(updatedNode);

        restNodeMockMvc.perform(put("/api/nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeDTO)))
            .andExpect(status().isOk());

        // Validate the Node in the database
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeUpdate);
        Node testNode = nodeList.get(nodeList.size() - 1);
        assertThat(testNode.getNodeId()).isEqualTo(UPDATED_NODE_ID);
        assertThat(testNode.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNode.getTotalCapacity()).isEqualTo(UPDATED_TOTAL_CAPACITY);
        assertThat(testNode.getUsedCapacity()).isEqualTo(UPDATED_USED_CAPACITY);
        assertThat(testNode.getFlavour()).isEqualTo(UPDATED_FLAVOUR);
        assertThat(testNode.getOs()).isEqualTo(UPDATED_OS);
        assertThat(testNode.getCpu()).isEqualTo(UPDATED_CPU);
    }

    @Test
    @Transactional
    public void updateNonExistingNode() throws Exception {
        int databaseSizeBeforeUpdate = nodeRepository.findAll().size();

        // Create the Node
        NodeDTO nodeDTO = nodeMapper.toDto(node);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNodeMockMvc.perform(put("/api/nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Node in the database
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNode() throws Exception {
        // Initialize the database
        nodeRepository.saveAndFlush(node);

        int databaseSizeBeforeDelete = nodeRepository.findAll().size();

        // Delete the node
        restNodeMockMvc.perform(delete("/api/nodes/{id}", node.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Node.class);
        Node node1 = new Node();
        node1.setId(1L);
        Node node2 = new Node();
        node2.setId(node1.getId());
        assertThat(node1).isEqualTo(node2);
        node2.setId(2L);
        assertThat(node1).isNotEqualTo(node2);
        node1.setId(null);
        assertThat(node1).isNotEqualTo(node2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NodeDTO.class);
        NodeDTO nodeDTO1 = new NodeDTO();
        nodeDTO1.setId(1L);
        NodeDTO nodeDTO2 = new NodeDTO();
        assertThat(nodeDTO1).isNotEqualTo(nodeDTO2);
        nodeDTO2.setId(nodeDTO1.getId());
        assertThat(nodeDTO1).isEqualTo(nodeDTO2);
        nodeDTO2.setId(2L);
        assertThat(nodeDTO1).isNotEqualTo(nodeDTO2);
        nodeDTO1.setId(null);
        assertThat(nodeDTO1).isNotEqualTo(nodeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(nodeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(nodeMapper.fromId(null)).isNull();
    }
}
