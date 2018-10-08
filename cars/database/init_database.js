// let error = true;

let res = [
    db.cars.createIndex({ regNumber: 1 }, { unique: true }),
    db.cars.save({
        regNumber: 'ABC-123',
        brand: 'TOYOTA',
        ownerName: 'Sami Laakso',
        _class: 'com.carservice.customercars.repository.CarEntity'
    }),
    db.cars.save({
        regNumber: 'TZR-125',
        brand: 'VOLVO',
        ownerName: 'Mikko Peltola',
        _class: 'com.carservice.customercars.repository.CarEntity'
    }),
    db.cars.save({
        regNumber: 'GPE-539',
        brand: 'VOLKSWAGEN',
        ownerName: 'Jarno Lahti',
        _class: 'com.carservice.customercars.repository.CarEntity'
    }),
];

printjson(res);

// if (error) {
//     print('Error, exiting');
//     quit(1)
// }