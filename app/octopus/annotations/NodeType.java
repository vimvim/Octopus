package octopus.annotations;

import repositories.NodesRepo;
import services.NodeService;

/**
 *
 */
public @interface NodeType {

    String name();

    Class<? extends NodeService> service();
    Class<? extends NodesRepo> repo();
}
