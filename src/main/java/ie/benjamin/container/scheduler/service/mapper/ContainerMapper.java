package ie.benjamin.container.scheduler.service.mapper;

import ie.benjamin.container.scheduler.domain.*;
import ie.benjamin.container.scheduler.service.dto.ContainerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Container} and its DTO {@link ContainerDTO}.
 */
@Mapper(componentModel = "spring", uses = {NodeMapper.class})
public interface ContainerMapper extends EntityMapper<ContainerDTO, Container> {

    @Mapping(source = "node.id", target = "nodeId")
    @Mapping(source = "node.name", target = "nodeName")
    ContainerDTO toDto(Container container);

    @Mapping(source = "nodeId", target = "node")
    Container toEntity(ContainerDTO containerDTO);

    default Container fromId(Long id) {
        if (id == null) {
            return null;
        }
        Container container = new Container();
        container.setId(id);
        return container;
    }
}
