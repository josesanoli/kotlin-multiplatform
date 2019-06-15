//
//  ViewController.swift
//  kotlin-multiplatform-ios
//
//  Created by Jose Sanchez Olivero on 15/06/2019.
//  Copyright © 2019 Jolusan. All rights reserved.
//

import UIKit
import KotlinShared

class ViewController: UIViewController, PresenterInterface {
    func userId(userId: Int32) {
        textLabel.text = String(userId)
    }
    
    func error() {
        textLabel.text = "Invalid user"
    }
    
    var presenter = MainPresenter()
    
    @IBOutlet weak var textLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //let user = CustomClass(name: "name_iOS", id: 56754, secondName: "rgdf")
        
        presenter.delegate = self
        presenter.getUserIdWithInterface()
        
        let test = SerializableClass(user: "xcode", number: 1234567)
        let testString = test.toJsonString()
        print(testString)

        let data = presenter.toData(json: "{\"user\":\"test xcode class\",\"number\":1234}")
        print(data)
        
    }
    
    
}


