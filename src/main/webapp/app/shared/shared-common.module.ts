import { NgModule } from '@angular/core';

import { ContainerSchedulerSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [ContainerSchedulerSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [ContainerSchedulerSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ContainerSchedulerSharedCommonModule {}
