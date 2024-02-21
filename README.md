# Utils-Kotlin
utils-kotlin is a Kotlin library for fast kotlin development

>Package: **br.dev.schirmer.utils.kotlin**<br/>
>Developer: **Claudio Schirmer Guedes**

## Statements

### Guard
```kotlin
guard(condition: Boolean, onFailure: () -> Nothing)
```

## Extensions

### from Any
#### Json
```kotlin
TObject.toJson<TObject>(
    alphabeticalOrder: Boolean = true,
    exportOptionsInclude: ExportOptionsInclude = ExportOptionsInclude.NON_EMPTY
) : String
```

### from String

#### Json
```kotlin
String.toClass<TObject>(): TObject
```

#### Cryptography
```kotlin
String.encrypt(key: String): String

String.decrypt(key: String): String

String.encryptURL(key: String): String

String.decryptURL(key: String): String

String.encryptHash(): String

String.equalsHash(hash: String): Boolean
```

#### Transformations
```kotlin
String.onlyDigits<TResult>(): TResult

String.removeDiacritics(): String
```