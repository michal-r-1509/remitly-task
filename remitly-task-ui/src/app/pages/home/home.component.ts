import {Component} from '@angular/core';
import {ConverterService} from "../../service/converter.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RequestDto} from "../dto/requestDto";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  currency_1: string = "GBP";
  currency_2: string = "PLN";
  toConvertCurrency: string = this.currency_1;
  convertedCurrency: string = this.currency_2;
  exchangeRate: string = "";
  i: number = 0;

  value_1_Control = new FormControl('',
    [Validators.required, Validators.pattern("^\\d{1,9}(\\.\\d{1,2})?$")]);
  value_2_Control = new FormControl('',
    [Validators.required, Validators.pattern("^\\d{1,9}(\\.\\d{1,2})?$")]);

/*  group = new FormGroup({
    value: this.value_1_Control
  })*/

  constructor(private converterService: ConverterService) {
  }

  convertSingleValueEvent(currency: String) {
    if (currency == this.currency_1 && this.value_1_Control.invalid) {
      return;
    }
    if (currency == this.currency_2 && this.value_2_Control.invalid) {
      return;
    }
    let value;
    let inputCurrency;
    let outputCurrency;
    if (currency == this.currency_1) {
      value = this.value_1_Control.value
      inputCurrency = this.currency_1;
      outputCurrency = this.currency_2
    } else if (currency == this.currency_2) {
      value = this.value_2_Control.value
      inputCurrency = this.currency_2;
      outputCurrency = this.currency_1
    } else {
      return;
    }
    const data: RequestDto = new RequestDto();
    data.baseCurrency = this.currency_1;
    data.inputCurrency = inputCurrency;
    data.outputCurrency = outputCurrency;
    data.value = value ?? "0";

    this.converterService.convertSingleValue(data).pipe().subscribe({
      next: response => {
        if (currency == this.currency_1) {
          this.value_2_Control.setValue(response.result)
          this.toConvertCurrency = this.currency_1;
          this.convertedCurrency = this.currency_2;
        }else if (currency == this.currency_2){
          this.value_1_Control.setValue(response.result);
          this.toConvertCurrency = this.currency_2;
          this.convertedCurrency = this.currency_1;
        }
        this.exchangeRate = response.exchangeRate;
      },
      error: () => console.log("something gone wrong")
    });
    console.log("ready to send " + data.value + " :: " + data.inputCurrency + " :: " + data.outputCurrency);
  }
}
