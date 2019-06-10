import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContainerSchedulerSharedModule } from 'app/shared';
import {
  NodeComponent,
  NodeDetailComponent,
  NodeUpdateComponent,
  NodeDeletePopupComponent,
  NodeDeleteDialogComponent,
  nodeRoute,
  nodePopupRoute
} from './';

const ENTITY_STATES = [...nodeRoute, ...nodePopupRoute];

@NgModule({
  imports: [ContainerSchedulerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [NodeComponent, NodeDetailComponent, NodeUpdateComponent, NodeDeleteDialogComponent, NodeDeletePopupComponent],
  entryComponents: [NodeComponent, NodeUpdateComponent, NodeDeleteDialogComponent, NodeDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContainerSchedulerNodeModule {}
