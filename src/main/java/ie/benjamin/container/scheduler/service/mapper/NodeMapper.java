package ie.benjamin.container.scheduler.service.mapper;

import ie.benjamin.container.scheduler.domain.*;
import ie.benjamin.container.scheduler.service.dto.NodeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Node} and its DTO {@link NodeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NodeMapper extends EntityMapper<NodeDTO, Node> {



    default Node fromId(Long id) {
        if (id == null) {
            return null;
        }
        Node node = new Node();
        node.setId(id);
        return node;
    }
}
