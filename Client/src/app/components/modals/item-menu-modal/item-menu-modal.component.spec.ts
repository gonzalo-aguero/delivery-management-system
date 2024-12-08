import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemMenuModalComponent } from './item-menu-modal.component';

describe('ItemMenuModalComponent', () => {
  let component: ItemMenuModalComponent;
  let fixture: ComponentFixture<ItemMenuModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ItemMenuModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ItemMenuModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
