import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpConnectComponent } from './up-connect.component';

describe('UpConnectComponent', () => {
  let component: UpConnectComponent;
  let fixture: ComponentFixture<UpConnectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpConnectComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpConnectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
