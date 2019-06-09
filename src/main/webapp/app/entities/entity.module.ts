import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'node',
        loadChildren: './node/node.module#ContainerSchedulerNodeModule'
      },
      {
        path: 'container',
        loadChildren: './container/container.module#ContainerSchedulerContainerModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContainerSchedulerEntityModule {}
