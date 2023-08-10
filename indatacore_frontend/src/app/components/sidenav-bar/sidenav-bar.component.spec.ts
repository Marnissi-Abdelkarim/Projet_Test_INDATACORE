import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidenavBarComponent } from './sidenav-bar.component';

describe('SidenavBarComponent', () => {
  let component: SidenavBarComponent;
  let fixture: ComponentFixture<SidenavBarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SidenavBarComponent]
    });
    fixture = TestBed.createComponent(SidenavBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
