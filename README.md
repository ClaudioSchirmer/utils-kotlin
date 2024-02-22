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
toJson<TObject>(
    alphabeticalOrder: Boolean = true,
    exportOptionsInclude: ExportOptionsInclude = ExportOptionsInclude.NON_EMPTY
) : String
```

### from String

#### Json
```kotlin
toClass<TObject>(): TObject
```

#### Cryptography
```kotlin
encrypt(key: String): String

decrypt(key: String): String

encryptURL(key: String): String

decryptURL(key: String): String

encryptHash(): String

equalsHash(hash: String): Boolean
```

#### Transformations
```kotlin
onlyDigits<TResult>(): TResult

removeDiacritics(): String
```