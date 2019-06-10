export interface INode {
  id?: number;
  nodeId?: string;
  name?: string;
  totalCapacity?: number;
  usedCapacity?: number;
  flavour?: string;
  os?: string;
  cpu?: string;
}

export class Node implements INode {
  constructor(
    public id?: number,
    public nodeId?: string,
    public name?: string,
    public totalCapacity?: number,
    public usedCapacity?: number,
    public flavour?: string,
    public os?: string,
    public cpu?: string
  ) {}
}
