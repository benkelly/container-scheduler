export interface IContainer {
  id?: number;
  containerId?: string;
  name?: string;
  image?: string;
  status?: string;
  flavour?: string;
  os?: string;
  cpu?: string;
  nodeName?: string;
  nodeId?: number;
}

export class Container implements IContainer {
  constructor(
    public id?: number,
    public containerId?: string,
    public name?: string,
    public image?: string,
    public status?: string,
    public flavour?: string,
    public os?: string,
    public cpu?: string,
    public nodeName?: string,
    public nodeId?: number
  ) {}
}
