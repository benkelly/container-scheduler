import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContainerSchedulerSharedModule } from 'app/shared';
import {
  ContainerComponent,
  ContainerDetailComponent,
  ContainerUpdateComponent,
  ContainerDeletePopupComponent,
  ContainerDeleteDialogComponent,
  containerRoute,
  containerPopupRoute
} from './';

const ENTITY_STATES = [...containerRoute, ...containerPopupRoute];

@NgModule({
  imports: [ContainerSchedulerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ContainerComponent,
    ContainerDetailComponent,
    ContainerUpdateComponent,
    ContainerDeleteDialogComponent,
    ContainerDeletePopupComponent
  ],
  entryComponents: [ContainerComponent, ContainerUpdateComponent, ContainerDeleteDialogComponent, ContainerDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContainerSchedulerContainerModule {}
