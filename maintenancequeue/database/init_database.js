// let error = true;

let res = [
    db.container.createIndex({ lastName: 1 }, { unique: true }),
    db.container.save({ firstName: 'Mikko', lastName: 'Laakso' }),
    db.container.save({ firstName: 'Sakke', lastName: 'Sandal' }),
    db.container.save({ firstName: 'Tommi', lastName: 'Rauhala' }),

    db.jobs.createIndex({ name: 1 }, { unique: true }),
    db.jobs.save({ name: 'Job1', jobState: 'PENDING' }),
    db.jobs.save({ name: 'Job2', jobState: 'PENDING' }),
    db.jobs.save({ name: 'Job3', jobState: 'PENDING' }),
];

printjson(res);

// if (error) {
//     print('Error, exiting');
//     quit(1)
// }