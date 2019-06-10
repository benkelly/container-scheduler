import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { INode, Node } from 'app/shared/model/node.model';
import { NodeService } from './node.service';

@Component({
  selector: 'jhi-node-update',
  templateUrl: './node-update.component.html'
})
export class NodeUpdateComponent implements OnInit {
  node: INode;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nodeId: [null, [Validators.required]],
    name: [null, [Validators.required]],
    totalCapacity: [],
    usedCapacity: [],
    flavour: [],
    os: [],
    cpu: []
  });

  constructor(protected nodeService: NodeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ node }) => {
      this.updateForm(node);
      this.node = node;
    });
  }

  updateForm(node: INode) {
    this.editForm.patchValue({
      id: node.id,
      nodeId: node.nodeId,
      name: node.name,
      totalCapacity: node.totalCapacity,
      usedCapacity: node.usedCapacity,
      flavour: node.flavour,
      os: node.os,
      cpu: node.cpu
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const node = this.createFromForm();
    if (node.id !== undefined) {
      this.subscribeToSaveResponse(this.nodeService.update(node));
    } else {
      this.subscribeToSaveResponse(this.nodeService.create(node));
    }
  }

  private createFromForm(): INode {
    const entity = {
      ...new Node(),
      id: this.editForm.get(['id']).value,
      nodeId: this.editForm.get(['nodeId']).value,
      name: this.editForm.get(['name']).value,
      totalCapacity: this.editForm.get(['totalCapacity']).value,
      usedCapacity: this.editForm.get(['usedCapacity']).value,
      flavour: this.editForm.get(['flavour']).value,
      os: this.editForm.get(['os']).value,
      cpu: this.editForm.get(['cpu']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INode>>) {
    result.subscribe((res: HttpResponse<INode>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
