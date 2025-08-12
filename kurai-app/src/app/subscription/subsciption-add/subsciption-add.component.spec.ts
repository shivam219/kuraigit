import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubsciptionAddComponent } from './subsciption-add.component';

describe('SubsciptionAddComponent', () => {
  let component: SubsciptionAddComponent;
  let fixture: ComponentFixture<SubsciptionAddComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubsciptionAddComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubsciptionAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
