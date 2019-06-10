import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IContainer, Container } from 'app/shared/model/container.model';
import { ContainerService } from './container.service';
import { INode } from 'app/shared/model/node.model';
import { NodeService } from 'app/entities/node';

@Component({
  selector: 'jhi-container-update',
  templateUrl: './container-update.component.html'
})
export class ContainerUpdateComponent implements OnInit {
  container: IContainer;
  isSaving: boolean;

  nodes: INode[];

  editForm = this.fb.group({
    id: [],
    containerId: [null, [Validators.required]],
    name: [null, [Validators.required]],
    image: [],
    status: [],
    flavour: [],
    os: [],
    cpu: [],
    nodeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected containerService: ContainerService,
    protected nodeService: NodeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ container }) => {
      this.updateForm(container);
      this.container = container;
    });
    this.nodeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<INode[]>) => mayBeOk.ok),
        map((response: HttpResponse<INode[]>) => response.body)
      )
      .subscribe((res: INode[]) => (this.nodes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(container: IContainer) {
    this.editForm.patchValue({
      id: container.id,
      containerId: container.containerId,
      name: container.name,
      image: container.image,
      status: container.status,
      flavour: container.flavour,
      os: container.os,
      cpu: container.cpu,
      nodeId: container.nodeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const container = this.createFromForm();
    if (container.id !== undefined) {
      this.subscribeToSaveResponse(this.containerService.update(container));
    } else {
      this.subscribeToSaveResponse(this.containerService.create(container));
    }
  }

  private createFromForm(): IContainer {
    const entity = {
      ...new Container(),
      id: this.editForm.get(['id']).value,
      containerId: this.editForm.get(['containerId']).value,
      name: this.editForm.get(['name']).value,
      image: this.editForm.get(['image']).value,
      status: this.editForm.get(['status']).value,
      flavour: this.editForm.get(['flavour']).value,
      os: this.editForm.get(['os']).value,
      cpu: this.editForm.get(['cpu']).value,
      nodeId: this.editForm.get(['nodeId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContainer>>) {
    result.subscribe((res: HttpResponse<IContainer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackNodeById(index: number, item: INode) {
    return item.id;
  }
}
