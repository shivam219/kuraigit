import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpiConnectComponent } from './upi-connect.component';

describe('UpiConnectComponent', () => {
  let component: UpiConnectComponent;
  let fixture: ComponentFixture<UpiConnectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpiConnectComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpiConnectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
