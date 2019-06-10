/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContainerSchedulerTestModule } from '../../../test.module';
import { NodeDetailComponent } from 'app/entities/node/node-detail.component';
import { Node } from 'app/shared/model/node.model';

describe('Component Tests', () => {
  describe('Node Management Detail Component', () => {
    let comp: NodeDetailComponent;
    let fixture: ComponentFixture<NodeDetailComponent>;
    const route = ({ data: of({ node: new Node(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContainerSchedulerTestModule],
        declarations: [NodeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NodeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NodeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.node).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
