import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import {
  ContainerSchedulerSharedLibsModule,
  ContainerSchedulerSharedCommonModule,
  JhiLoginModalComponent,
  HasAnyAuthorityDirective
} from './';

@NgModule({
  imports: [ContainerSchedulerSharedLibsModule, ContainerSchedulerSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [ContainerSchedulerSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContainerSchedulerSharedModule {
  static forRoot() {
    return {
      ngModule: ContainerSchedulerSharedModule
    };
  }
}
